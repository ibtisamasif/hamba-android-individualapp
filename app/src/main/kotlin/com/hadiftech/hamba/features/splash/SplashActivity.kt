package com.hadiftech.hamba.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.core.session.Session
import com.hadiftech.hamba.features.dashboard.DashboardActivity
import com.hadiftech.hamba.features.login.LoginActivity

class SplashActivity : HambaBaseActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 2000 //2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            if (Session.isSessionAvailable()) {
                moveToDashboardActivity()
            } else {
                moveToLoginActivity()
            }
        }
    }

    private fun moveToDashboardActivity(){
        val dashboardIntent = Intent(this, DashboardActivity::class.java)
        startActivity(dashboardIntent)
        finish()
    }

    private fun moveToLoginActivity(){
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
    }
}
