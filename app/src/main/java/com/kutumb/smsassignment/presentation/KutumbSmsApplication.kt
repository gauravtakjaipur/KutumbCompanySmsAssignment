package com.kutumb.smsassignment.presentation

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.kutumb.smsassignment.di.DIHelper

open class KutumbSmsApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        MultiDex.install(this)
        setupDI()
    }

    open fun setupDI() {
        diHelper = DIHelper(this)
        diHelper.mainComponent.inject(this)
    }
    companion object{
        lateinit var mContext: Context
        lateinit var diHelper: DIHelper
    }
}