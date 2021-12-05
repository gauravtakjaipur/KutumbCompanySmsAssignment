package com.kutumb.smsassignment.presentation.smsListing

import com.kutumb.smsassignment.data.modelClasses.SmsMessageData


interface SmsListItemClickInterface {
    fun onItemClick(smsMessageData: SmsMessageData)
}