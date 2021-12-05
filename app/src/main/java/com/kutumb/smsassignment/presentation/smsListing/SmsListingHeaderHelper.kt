package com.kutumb.smsassignment.presentation.smsListing

import android.util.Log

object SmsListingHeaderHelper {
    var headerValue: String = ""

    fun isHeaderViewShowHide(smsTimeStamp: Long) : Pair<String,Boolean> {
        val currentTimeInMilliSeconds = System.currentTimeMillis()
        Log.i("isHeaderViewShowHide: ","time = $smsTimeStamp $currentTimeInMilliSeconds")
        val diffTimeInHours = ((currentTimeInMilliSeconds - smsTimeStamp) / (1000*60*60)).toInt()
        val diffTimeInDays = ((currentTimeInMilliSeconds - smsTimeStamp) / (1000*60*60*24)).toInt()
        var diffTimeForHeader = ""
        Log.i("isHeaderViewShowHide: ","Diff = $diffTimeInHours")
        when {
         diffTimeInDays >= 1 -> {
                diffTimeForHeader = if (diffTimeInDays == 1) {
                    "$diffTimeInDays day ago"
                } else {
                    "$diffTimeInDays days ago"
                }
            }
            else -> {
                when {
                    diffTimeInHours < 1 -> {
                        diffTimeForHeader = "Recent"//"0 hour ago"
                    }
                    diffTimeInHours < 2 -> {
                        diffTimeForHeader = "1 hour ago"
                    }
                    diffTimeInHours < 3 -> {
                        diffTimeForHeader = "2 hours ago"
                    }
                    diffTimeInHours in 3..5 -> {
                        diffTimeForHeader = "3 hours ago"
                    }
                    diffTimeInHours in 6..11 -> {
                        diffTimeForHeader = "6 hours ago"
                    }
                    diffTimeInHours in 12..23 -> {
                        diffTimeForHeader = "12 hours ago"
                    }
                }
            }
        }
        return if (diffTimeForHeader == headerValue) {
            Pair("",false)
        } else {
            headerValue = diffTimeForHeader
            Pair(headerValue,true)
        }
     }
}