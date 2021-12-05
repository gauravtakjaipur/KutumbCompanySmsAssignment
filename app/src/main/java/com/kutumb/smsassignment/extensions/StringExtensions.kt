package com.kutumb.smsassignment.extensions

const val EMPTY = ""

fun String.isEmpty() = (this.trimEnd() == EMPTY)

fun String.isNotEmpty(): Boolean {
    return !this.isEmpty()
}
