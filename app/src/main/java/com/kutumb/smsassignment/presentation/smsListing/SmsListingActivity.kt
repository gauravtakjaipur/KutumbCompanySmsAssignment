package com.kutumb.smsassignment.presentation.smsListing

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.lifecycleScope
import com.kutumb.smsassignment.R
import com.kutumb.smsassignment.data.modelClasses.SmsMessageData
import com.kutumb.smsassignment.helpers.IntentConstants
import com.kutumb.smsassignment.helpers.NotificationHelper
import com.kutumb.smsassignment.helpers.PermissionHelper
import com.kutumb.smsassignment.presentation.KutumbSmsApplication
import com.kutumb.smsassignment.presentation.base.BaseActivity
import com.kutumb.smsassignment.presentation.base.ViewModelFactory
import com.kutumb.smsassignment.presentation.smsDetails.SmsDetailsActivity
import kotlinx.android.synthetic.main.activity_sms_listing.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class SmsListingActivity : BaseActivity() {


    override fun inject() {
        KutumbSmsApplication.diHelper.createSmsListingComponent().inject(this)
    }

    override fun releaseComponent() {
        KutumbSmsApplication.diHelper.releaseSmsListingComponent()
    }

    @Inject
    lateinit var smsListingVMFactory: ViewModelFactory<SmsListingViewModel>

    @Inject
    lateinit var kutumbSmsApplication: KutumbSmsApplication

    @VisibleForTesting
    val viewModel: SmsListingViewModel by viewModels { smsListingVMFactory }

    private var pagingAdapter: SmsMessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_listing)
        setUpView()
        checkPermissionAndLoadSmsData()
        NotificationHelper.removeSmsNotification(kutumbSmsApplication)
    }

    private fun setUpView() {
        setSupportActionBar(toolbar)
        val listItemClickInterface = object : SmsListItemClickInterface {
            override fun onItemClick(smsMessageData: SmsMessageData) {
                startActivity(Intent(this@SmsListingActivity, SmsDetailsActivity::class.java).putExtra(IntentConstants.SMS_MESSAGE_DATA, smsMessageData))
            }
        }
        pagingAdapter = SmsMessageAdapter(SmsMessageComparator, listItemClickInterface)
        rvSmsMessagesList.adapter = pagingAdapter
    }

    private fun checkPermissionAndLoadSmsData() {
        if(PermissionHelper.checkIfAlreadyHaveSmsPermission(this)) {
           performSmsReadingOperation()
        } else {
            PermissionHelper.askForSmsPermission(this)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(PermissionHelper.checkIfAlreadyHaveSmsPermission(this)) {
            performSmsReadingOperation()
        } else {
            PermissionHelper.displayGrantPermissionDialog(this, getString(R.string.grant_permission_sms), onSettingsTapped = {
                viewModel.isGoToSettingsTapped = true
                // this boolean will manage the permission asking once we reach again after allowing the sms permission from settings
            }, onCancelTapped = {
                finish()
                // when cancel tapped for permission dialog so no use of application running
            })
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun performSmsReadingOperation() {
        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                pagingAdapter?.submitData(pagingData)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(viewModel.isGoToSettingsTapped) {
            checkPermissionAndLoadSmsData()
        }
    }
}