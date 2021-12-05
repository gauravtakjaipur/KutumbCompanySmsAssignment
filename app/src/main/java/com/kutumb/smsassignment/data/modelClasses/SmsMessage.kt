package com.kutumb.smsassignment.data.modelClasses

import android.os.Parcel
import android.os.Parcelable


data class SmsMessage(
    var address: String? = null,
    var msgBodyText: String? = null,
    var readState: String? = null, //"0" for have not read sms and "1" for have read sms
    var time: Long = 0L,
    var type: Int = 0,// this is used to find its sent sms or received sms
    val headerValue: String? = null,
    val isShowHeader: Boolean = false
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeString(msgBodyText)
        parcel.writeString(readState)
        parcel.writeLong(time)
        parcel.writeInt(type)
        parcel.writeString(headerValue)
        parcel.writeByte(if (isShowHeader) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SmsMessage> {
        override fun createFromParcel(parcel: Parcel): SmsMessage {
            return SmsMessage(parcel)
        }

        override fun newArray(size: Int): Array<SmsMessage?> {
            return arrayOfNulls(size)
        }
    }
}