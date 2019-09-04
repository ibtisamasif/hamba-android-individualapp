package com.hadiftech.hamba.features.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.features.login.LoginActivity
import com.hadiftech.hamba.features.signup.enums.CodeVerificationType
import kotlinx.android.synthetic.main.activity_code_verification.*

class CodeVerificationActivity : HambaBaseActivity() {

    companion object {
        @JvmStatic val KEY_SIGN_UP_TYPE = "SignUpType"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_verification)

        var signUpType = intent.getSerializableExtra(KEY_SIGN_UP_TYPE) as CodeVerificationType
        setupScreenDisplay(signUpType)
    }

    fun onVerifyCodeClicked(btnVerifyCode: View) {
        moveToLoginActivity()
    }

    private fun moveToLoginActivity() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(loginIntent)
        finish()
    }

    private fun setupScreenDisplay(codeVerificationType: CodeVerificationType) {
        when (codeVerificationType) {
            CodeVerificationType.EmailVerification -> displayEmailVerification()
            CodeVerificationType.NumberVerification -> displayNumberVerification()
            CodeVerificationType.EmailAndNumberVerification -> displayEmailAndNumberVerification()
        }
    }

    private fun displayEmailVerification() {
        textView_codeInstructions.setText(R.string.code_instructions_email)
        editText_EmailCode.visibility = View.VISIBLE
        editText_NumberCode.visibility = View.INVISIBLE
    }

    private fun displayNumberVerification() {
        textView_codeInstructions.setText(R.string.code_instructions_number)
        editText_EmailCode.visibility = View.INVISIBLE
        editText_NumberCode.visibility = View.VISIBLE
    }

    private fun displayEmailAndNumberVerification() {
        textView_codeInstructions.setText(R.string.code_instructions_email_and_number)
        editText_EmailCode.visibility = View.VISIBLE
        editText_NumberCode.visibility = View.VISIBLE
    }
}