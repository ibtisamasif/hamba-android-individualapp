package com.hadiftech.hamba.features.forget_password

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity

class NewPasswordActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)
    }

    fun onPasswordClickedClicked(continueButton: View) {
        val passwordResetIntent = Intent(this, PasswordResetStatusActivity::class.java)
        startActivity(passwordResetIntent)
    }
}