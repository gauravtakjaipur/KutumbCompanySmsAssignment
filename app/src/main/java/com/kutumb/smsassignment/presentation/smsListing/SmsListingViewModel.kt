package com.kutumb.smsassignment.presentation.smsListing


import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kutumb.smsassignment.data.messageRepo.SmsMessagePagingSource
import com.kutumb.smsassignment.data.messageRepo.SmsMessageRepository
import com.kutumb.smsassignment.presentation.KutumbSmsApplication
import com.kutumb.smsassignment.presentation.base.BaseViewModel


class SmsListingViewModel(
    val kutumbSmsApplication: KutumbSmsApplication
) : BaseViewModel() {
    var isGoToSettingsTapped: Boolean = false
    val context: Context = kutumbSmsApplication.applicationContext
    private val repo =  SmsMessageRepository(kutumbSmsApplication.applicationContext)

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 10)
    ) {
        SmsMessagePagingSource(repo)
    }.flow.cachedIn(viewModelScope)
}