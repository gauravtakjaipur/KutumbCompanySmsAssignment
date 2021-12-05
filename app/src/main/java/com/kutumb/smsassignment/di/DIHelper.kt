package com.kutumb.smsassignment.di

import android.content.Context
import com.kutumb.smsassignment.di.modules.AppModule
import com.kutumb.smsassignment.di.smsDetails.SmsDetailsModule
import com.kutumb.smsassignment.di.smsDetails.SuperSmsDetailsSubComponent
import com.kutumb.smsassignment.di.smsListing.SmsListingModule
import com.kutumb.smsassignment.di.smsListing.SuperSmsListingSubComponent

open class DIHelper(context: Context) {

    open var mainComponent: SuperMainComponent = DaggerMainComponent.builder()
        .appModule(AppModule(context))
        .build()

    open var smsListingComponent: SuperSmsListingSubComponent? = null
    open var smsDetailsComponent: SuperSmsDetailsSubComponent? = null

      // ------------- SubComponents and release methods ------------------------//

    // --------------------- SMS Listing

    open fun createSmsListingComponent(): SuperSmsListingSubComponent {
        smsListingComponent = (mainComponent as MainComponent).plus(SmsListingModule())
        return smsListingComponent!!
    }

    open fun releaseSmsListingComponent() {
        smsListingComponent = null
    }

    // --------------------- SMS Details

    open fun createSmsDetailsComponent(): SuperSmsDetailsSubComponent {
        smsDetailsComponent = (mainComponent as MainComponent).plus(SmsDetailsModule())
        return smsDetailsComponent!!
    }

    open fun releaseSmsDetailsComponent() {
        smsDetailsComponent = null
    }
}
