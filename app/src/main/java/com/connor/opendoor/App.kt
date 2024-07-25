package com.connor.opendoor

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.Executors

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        lateinit var app: App private set
    }
}