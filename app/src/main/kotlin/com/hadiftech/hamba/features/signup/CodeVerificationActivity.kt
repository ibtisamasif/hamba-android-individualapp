package com.hadiftech.hamba.features.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.features.signup.enums.CodeVerificationType
import kotlinx.android.synthetic.main.activity_code_verification.*

class CodeVerificationActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_verification)

        setupScreenDisplay(CodeVerificationType.EmailAndNumberVerification)
    }

    fun onVerifyCodeClicked(btnVerifyCode: View) {
        // TODO: Do code verification
        moveToHelloUserActivity()
    }

    private fun moveToHelloUserActivity() {
        val helloUserIntent = Intent(this, HelloUserActivity::class.java)
        startActivity(helloUserIntent)
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