package com.hadiftech.hamba.core

import android.app.Application
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.session.Session
import com.hadiftech.hamba.core.session.User

class HambaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        APiManager.initialize()
        User.initialize(applicationContext)
        Session.initialize(applicationContext)
    }
}