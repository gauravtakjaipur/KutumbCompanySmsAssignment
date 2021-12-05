package com.kutumb.smsassignment.di

import com.kutumb.smsassignment.di.modules.AppModule
import com.kutumb.smsassignment.di.smsDetails.SmsDetailsModule
import com.kutumb.smsassignment.di.smsDetails.SmsDetailsSubComponent
import com.kutumb.smsassignment.di.smsListing.SmsListingModule
import com.kutumb.smsassignment.di.smsListing.SmsListingSubComponent
import com.kutumb.smsassignment.presentation.KutumbSmsApplication
import com.kutumb.smsassignment.presentation.base.BaseActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AppModule::class)
    ]
)
interface MainComponent : SuperMainComponent {
    fun plus(smsListingModule: SmsListingModule): SmsListingSubComponent
    fun plus(smsDetailsModule: SmsDetailsModule): SmsDetailsSubComponent
}

interface SuperMainComponent {
    fun inject(activity: BaseActivity)
    fun inject(app: KutumbSmsApplication)
}
