package com.kutumb.smsassignment.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import com.kutumb.smsassignment.data.modelClasses.SmsMessageType
import com.kutumb.smsassignment.helpers.NotificationHelper
import com.kutumb.smsassignment.presentation.smsListing.SmsListingActivity


class SmsBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        Log.i(TAG, "Intent received: " + intent.action)
        if (intent.action == SMS_RECEIVED) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<Any>?
                val messages: Array<SmsMessage?> = arrayOfNulls<SmsMessage>(pdus!!.size)
                for (i in pdus.indices) {
                    messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                }
                if (messages.size > -1) {
                    Log.i(TAG, "Message received: " + messages[0]?.messageBody)
                    if(messages[0] != null) {
                        val smsMessage = messages[0] as SmsMessage
                        val smsMessageData: com.kutumb.smsassignment.data.modelClasses.SmsMessage = com.kutumb.smsassignment.data.modelClasses.SmsMessage(smsMessage.displayOriginatingAddress,smsMessage.messageBody,"0",smsMessage.timestampMillis,SmsMessageType.RECEIVED.ordinal,"",false )
                        context?.let {
                            NotificationHelper.showNotification(
                                    it,
                                    smsMessageData.address,
                                    smsMessageData.msgBodyText,
                                    Intent(it, SmsListingActivity::class.java)
                                            //.putExtra(IntentConstants.SMS_MESSAGE_DATA,smsMessageData) //Not needed for now
                            )
                        }
                    }

                }
            }
        }
    }

    companion object {
        private const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
        private const val TAG = "SmsBroadcastReceiver"
    }
}