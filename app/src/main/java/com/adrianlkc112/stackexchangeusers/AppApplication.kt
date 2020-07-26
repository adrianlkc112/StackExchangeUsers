package com.adrianlkc112.stackexchangeusers

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}