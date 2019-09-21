package com.hadiftech.hamba.core

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.session.Session

class HambaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        APiManager.initialize()
        Session.initialize(applicationContext)
        Fresco.initialize(this)
    }
}