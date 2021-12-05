package com.kutumb.smsassignment.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kutumb.smsassignment.presentation.KutumbSmsApplication

abstract class BaseActivity : AppCompatActivity() {

    abstract fun inject()
    abstract fun releaseComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KutumbSmsApplication.diHelper.mainComponent.inject(this)
        inject()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseComponent()
    }
}