package com.kutumb.smsassignment.di.smsDetails


import com.kutumb.smsassignment.presentation.base.ViewModelFactory
import com.kutumb.smsassignment.presentation.smsDetails.SmsDetailsViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ObsoleteCoroutinesApi

@Module
class SmsDetailsModule {

    @ObsoleteCoroutinesApi
    @Provides
    fun provideSmsDetailsVMFactory(): ViewModelFactory<SmsDetailsViewModel> {

        return ViewModelFactory {
            SmsDetailsViewModel()
        }
    }
}
