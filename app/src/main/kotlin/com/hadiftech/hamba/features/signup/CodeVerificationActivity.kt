package com.hadiftech.hamba.features.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.core.enums.DialogTheme
import com.hadiftech.hamba.features.login.LoginActivity
import com.hadiftech.hamba.core.enums.UserType
import com.hadiftech.hamba.core.listeners.DialogButtonClickListener
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.features.signup.code_verification_service.VerifyOtpRequest
import com.hadiftech.hamba.features.signup.code_verification_service.VerifyOtpResponse
import com.hadiftech.hamba.features.signup.resend_otp_service.ResendOtpRequest
import com.hadiftech.hamba.features.signup.resend_otp_service.ResendOtpResponse
import kotlinx.android.synthetic.main.activity_code_verification.*

class CodeVerificationActivity : HambaBaseActivity() {

    private var userType = UserType.INDIVIDUAL

    companion object {
        @JvmStatic val KEY_USER_TYPE = "UserType"
        @JvmStatic val KEY_USER_EMAIL = "UserEMAIL"
        @JvmStatic val KEY_USER_NUMBER = "UserNUMBER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_verification)

        userType = intent.getSerializableExtra(KEY_USER_TYPE) as UserType
        setupScreenDisplay()
    }

    fun onVerifyCodeClicked(btnVerifyCode: View) {

        if (checkValidations()) {
            when (userType) {
                UserType.INDIVIDUAL -> {

                    val verifyOtpRequest = VerifyOtpRequest()
                    verifyOtpRequest.userType = userType.name

                    if (editText_NumberCode.visibility == View.VISIBLE) {
                        verifyOtpRequest.otpCode = editText_NumberCode.text.toString()
                    } else {
                        verifyOtpRequest.otpCode = editText_EmailCode.text.toString()
                    }
                    APiManager.verifyOtpCode(this, this, verifyOtpRequest)
                }
                UserType.BUSINESS_EMPLOYEE, UserType.BUSINESS_OWNER -> {
                    val verifyBusinessOtpRequest = VerifyOtpRequest()
                    verifyBusinessOtpRequest.userType = userType.name
                    verifyBusinessOtpRequest.otpCode = editText_NumberCode.text.toString()
                    verifyBusinessOtpRequest.otpCodeEmail = editText_EmailCode.text.toString()
                    APiManager.verifyOtpCode(this, this, verifyBusinessOtpRequest)
                }
            }
        }
    }

    fun onProblemReceivingCodeClicked (view: View) {
        val resendOtpRequest = ResendOtpRequest()
        resendOtpRequest.userType = userType.name
        resendOtpRequest.email = intent.getStringExtra(KEY_USER_EMAIL)
        resendOtpRequest.number = intent.getStringExtra(KEY_USER_NUMBER)
        APiManager.resendOtpCode(this, this, resendOtpRequest)
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is VerifyOtpResponse) {
            if (apiResponse.success!!){
                AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, apiResponse.message, getString(R.string.login),
                    object : DialogButtonClickListener {
                        override fun onClick(alertDialog: AlertDialog) {
                            moveToLoginActivity()
                        }
                    })
            } else {
                AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, apiResponse.message)
            }
        }

        if (apiResponse is ResendOtpResponse) {
            if (apiResponse.success!!){
                AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, apiResponse.message, getString(R.string.verify),
                    object : DialogButtonClickListener {
                        override fun onClick(alertDialog: AlertDialog) {
                            alertDialog.dismiss()
                        }
                    })
            } else {
                AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, apiResponse.message)
            }
        }
    }

    private fun moveToLoginActivity() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(loginIntent)
        finish()
    }

    private fun setupScreenDisplay() {
        when (userType) {
            UserType.INDIVIDUAL -> {
                if (intent.getStringExtra(KEY_USER_EMAIL).isEmpty()) {
                    displayNumberVerification()
                } else {
                    displayEmailVerification()
                }
            }
            UserType.BUSINESS_EMPLOYEE -> displayEmailVerification()
            UserType.BUSINESS_OWNER -> displayEmailAndNumberVerification()
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

    private fun checkValidations() : Boolean {

        if(editText_NumberCode.isVisible && editText_NumberCode.text.isEmpty()) {
            editText_NumberCode.setError(getString(R.string.please_enter_otp_code))
            return false
        }

        if(editText_EmailCode.isVisible && editText_EmailCode.text.isEmpty()) {
            editText_EmailCode.setError(getString(R.string.please_enter_otp_code))
            return false
        }

        return true
    }
}