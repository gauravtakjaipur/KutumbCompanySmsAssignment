package com.kutumb.smsassignment.di.smsListing

import com.kutumb.smsassignment.presentation.smsListing.SmsListingActivity
import dagger.Subcomponent

@SmsListingScope
@Subcomponent(modules = [SmsListingModule::class])
interface SmsListingSubComponent : SuperSmsListingSubComponent

interface SuperSmsListingSubComponent {
    fun inject(smsListingActivity: SmsListingActivity)
}
