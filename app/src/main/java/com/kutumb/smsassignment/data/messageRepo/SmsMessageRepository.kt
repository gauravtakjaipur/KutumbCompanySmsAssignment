package com.kutumb.smsassignment.data.messageRepo

import android.content.Context
import android.provider.Telephony.Sms
import com.kutumb.smsassignment.data.modelClasses.SmsMessageData
import com.kutumb.smsassignment.data.modelClasses.SmsMessageType
import com.kutumb.smsassignment.presentation.smsListing.SmsListingHeaderHelper

/**
 * Repository to retrieve SMS messages from Telephony content provider.
 */
class SmsMessageRepository (
    private val context: Context) {

    /**
     * Retrieve SMS messages by page
     * @return
     */
    fun getSmsMessages(limit: Int, offset: Int): List<SmsMessageData>
    {
        val messages = mutableListOf<SmsMessageData>()
        val cursor = context.contentResolver.query(
            Sms.CONTENT_URI,
            arrayOf<String>(
                Sms.ADDRESS,
                Sms.BODY,
                Sms.DATE,
                Sms.READ,
                Sms.SEEN,
                Sms.TYPE
            ),
            null,
            null,
            Sms.Inbox.DEFAULT_SORT_ORDER + " LIMIT " + limit + " OFFSET " + offset
        )
        if (cursor != null) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val phoneOrSender =
                    cursor.getString(cursor.getColumnIndexOrThrow(Sms.ADDRESS))
                val timeStamp = cursor.getLong(cursor.getColumnIndexOrThrow(Sms.DATE))
                val msgText = cursor.getString(cursor.getColumnIndexOrThrow(Sms.BODY))
                val readStatus = cursor.getString(cursor.getColumnIndexOrThrow(Sms.READ))
                val smsType: SmsMessageType = if (cursor.getString(cursor.getColumnIndexOrThrow("type")).contains("1")) {
                    SmsMessageType.RECEIVED
                } else {
                    SmsMessageType.SENT
                }
                val headerTextIsShowHeaderPair = timeStamp.let { SmsListingHeaderHelper.isHeaderViewShowHide(it) }
                messages.add(SmsMessageData(phoneOrSender, msgText,readStatus, timeStamp,smsType.ordinal,headerTextIsShowHeaderPair.first,headerTextIsShowHeaderPair.second))
                cursor.moveToNext()
            }
            cursor.close()
        }
        return messages
    }
}