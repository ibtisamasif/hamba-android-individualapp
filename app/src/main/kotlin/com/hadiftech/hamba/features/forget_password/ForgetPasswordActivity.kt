package com.hadiftech.hamba.features.forget_password

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity

class ForgetPasswordActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
    }

    fun onContinueButtonClicked(continueButton: View) {
        val newPasswordIntent = Intent(this, NewPasswordActivity::class.java)
        startActivity(newPasswordIntent)
    }
}