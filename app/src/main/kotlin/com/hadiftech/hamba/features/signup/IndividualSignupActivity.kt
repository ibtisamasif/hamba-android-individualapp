package com.hadiftech.hamba.features.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity

class IndividualSignupActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_signup)
    }

    fun onSignUpButtonClicked(signUpButton: View) {
        moveToCodeVerificationActivity()
    }

    private fun moveToCodeVerificationActivity() {
        val codeVerificationIntent = Intent(this, CodeVerificationActivity::class.java)
        startActivity(codeVerificationIntent)
    }
}