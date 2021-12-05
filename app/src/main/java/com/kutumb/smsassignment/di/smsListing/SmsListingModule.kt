package com.kutumb.smsassignment.di.smsListing


import com.kutumb.smsassignment.presentation.KutumbSmsApplication
import com.kutumb.smsassignment.presentation.base.ViewModelFactory
import com.kutumb.smsassignment.presentation.smsListing.SmsListingViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ObsoleteCoroutinesApi

@Module
class SmsListingModule {

    @ObsoleteCoroutinesApi
    @Provides
    fun provideSmsListingVMFactory(
        kutumbSmsApplication: KutumbSmsApplication
    ): ViewModelFactory<SmsListingViewModel> {

        return ViewModelFactory {
            SmsListingViewModel(
                kutumbSmsApplication = kutumbSmsApplication
            )
        }
    }
}
