package com.kutumb.smsassignment.di.modules

import android.content.Context
import com.kutumb.smsassignment.presentation.KutumbSmsApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule constructor(context: Context) {

    private val appContext = context.applicationContext

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun provideAppInstance(): KutumbSmsApplication {
        return appContext as KutumbSmsApplication
    }
}
