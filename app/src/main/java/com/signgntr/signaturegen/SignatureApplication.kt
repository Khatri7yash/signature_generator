package com.signgntr.signaturegen

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SignatureApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}