package com.hadiftech.hamba.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.features.dashboard.DashboardActivity
import com.hadiftech.hamba.features.forget_password.ForgetPasswordActivity
import com.hadiftech.hamba.features.signup.HelloUserActivity
import com.hadiftech.hamba.features.signup.JoinUsActivity

class LoginActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onSignInButtonClicked(signInTextView: View) {
        val dashboardIntent = Intent(this, DashboardActivity::class.java)
        startActivity(dashboardIntent)
    }

    fun onCreateAccountClicked(createAccountTextView: View){
        val joinUsIntent = Intent(this, JoinUsActivity::class.java)
        startActivity(joinUsIntent)
    }

    fun onContinueAsGuestClicked(guestView: View) {
        val helloUserIntent = Intent(this, HelloUserActivity::class.java)
        startActivity(helloUserIntent)
    }

    fun onForgetPasswordClicked(forgetPasswordButton: View) {
        val forgetPasswordIntent = Intent(this, ForgetPasswordActivity::class.java)
        startActivity(forgetPasswordIntent)
    }
}
