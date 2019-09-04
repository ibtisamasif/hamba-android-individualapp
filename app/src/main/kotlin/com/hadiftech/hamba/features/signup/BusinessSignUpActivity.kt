package com.hadiftech.hamba.features.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.features.signup.enums.CodeVerificationType

class BusinessSignUpActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_sign_up)
    }

    fun onSignUpButtonClicked(signUpButton: View) {
        val codeVerificationIntent = Intent(this, CodeVerificationActivity::class.java)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_SIGN_UP_TYPE, CodeVerificationType.EmailVerification)
        startActivity(codeVerificationIntent)
    }
}