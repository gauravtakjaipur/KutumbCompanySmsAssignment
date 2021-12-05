package com.kutumb.smsassignment.presentation.smsListing

import com.kutumb.smsassignment.data.modelClasses.SmsMessage


interface SmsListItemClickInterface {
    fun onItemClick(smsMessageData: SmsMessage)
}