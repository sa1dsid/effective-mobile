package com.example.testtask

import android.app.Application
import com.example.testtask.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestTaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TestTaskApplication)
            modules(appModules)
        }
    }
}
