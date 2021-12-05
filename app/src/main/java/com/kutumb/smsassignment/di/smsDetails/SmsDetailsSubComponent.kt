package com.kutumb.smsassignment.di.smsDetails

import com.kutumb.smsassignment.presentation.smsDetails.SmsDetailsActivity
import dagger.Subcomponent

@SmsDetailsScope
@Subcomponent(modules = [SmsDetailsModule::class])
interface SmsDetailsSubComponent : SuperSmsDetailsSubComponent

interface SuperSmsDetailsSubComponent {
    fun inject(smsDetailsActivity: SmsDetailsActivity)
}
