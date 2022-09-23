package com.tmonet.hcetest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HceApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}