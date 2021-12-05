package com.kutumb.smsassignment.presentation.smsDetails

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.kutumb.smsassignment.R
import com.kutumb.smsassignment.data.modelClasses.SmsMessageData
import com.kutumb.smsassignment.data.modelClasses.SmsMessageType
import com.kutumb.smsassignment.extensions.getDateValueWithDateAndTime
import com.kutumb.smsassignment.helpers.IntentConstants
import com.kutumb.smsassignment.presentation.KutumbSmsApplication
import com.kutumb.smsassignment.presentation.base.BaseActivity
import com.kutumb.smsassignment.presentation.base.ViewModelFactory
import kotlinx.android.synthetic.main.activity_sms_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class SmsDetailsActivity: BaseActivity() {

    override fun inject() {
        KutumbSmsApplication.diHelper.createSmsDetailsComponent().inject(this)
    }

    override fun releaseComponent() {
        KutumbSmsApplication.diHelper.releaseSmsDetailsComponent()
    }

    @Inject
    lateinit var smsDetailsVMFactory: ViewModelFactory<SmsDetailsViewModel>

    @VisibleForTesting
    val viewModel: SmsDetailsViewModel by viewModels { smsDetailsVMFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_details)
        setUpView()
    }

    private fun setUpView() {
        viewModel.smsMessageData = intent.getParcelableExtra<SmsMessageData>(IntentConstants.SMS_MESSAGE_DATA)
        Log.i( "setUpView: ","Intent Data ${viewModel.smsMessageData?.address} ${viewModel.smsMessageData?.msgBodyText} ${viewModel.smsMessageData?.readState} ${viewModel.smsMessageData?.time?.getDateValueWithDateAndTime()}")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.sms_details)
        tvTitle.text = if(viewModel.smsMessageData?.type == SmsMessageType.RECEIVED.ordinal)  getString(R.string.sender,viewModel.smsMessageData?.address) else getString(R.string.receiver,viewModel.smsMessageData?.address)
        tvSubTitle.text = getString(R.string.date_time,viewModel.smsMessageData?.time?.getDateValueWithDateAndTime())
        tvSmsBody.text = getString(R.string.message_body,viewModel.smsMessageData?.msgBodyText)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}