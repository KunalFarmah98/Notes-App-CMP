package com.apps.kunalfarmah.notesmp

import android.app.Application
import di.KoinInitializer

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}