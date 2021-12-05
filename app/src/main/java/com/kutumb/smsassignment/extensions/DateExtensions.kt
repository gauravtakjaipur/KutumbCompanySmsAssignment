package com.kutumb.smsassignment.extensions

import java.text.SimpleDateFormat
import java.util.*

const val dateFormatWithTime: String = "MMM dd, yyyy h:mm a"

fun Long.getDateValueWithDateAndTime() : String {
    return SimpleDateFormat(dateFormatWithTime, Locale.ENGLISH).format(Date(this))
}