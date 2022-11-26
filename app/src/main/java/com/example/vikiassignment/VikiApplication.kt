package com.example.vikiassignment

import android.app.Application
import com.example.vikiassignment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VikiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VikiApplication)
            modules(appModule)
        }
    }
}