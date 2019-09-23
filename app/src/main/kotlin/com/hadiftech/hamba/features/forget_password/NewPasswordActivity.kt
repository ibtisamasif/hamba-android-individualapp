package com.hadiftech.hamba.features.forget_password

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.core.HambaUtils
import com.hadiftech.hamba.core.enums.UserType
import com.hadiftech.hamba.core.listeners.DialogButtonClickListener
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.features.forget_password.new_password_service.NewPasswordRequest
import com.hadiftech.hamba.features.forget_password.new_password_service.NewPasswordResponse
import com.hadiftech.hamba.features.signup.resend_otp_service.ResendOtpRequest
import com.hadiftech.hamba.features.signup.resend_otp_service.ResendOtpResponse
import kotlinx.android.synthetic.main.activity_new_password.*

class NewPasswordActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)
    }

    fun onResetPasswordClicked(continueButton: View) {

        if (checkValidations()) {

            val newPasswordRequest = NewPasswordRequest()
            newPasswordRequest.otpCode = editText_otpCode.getText()
            newPasswordRequest.newPassword = editText_newPassword.getText()

            val emailOrNumber = intent.getStringExtra(ForgetPasswordActivity.KEY_EMAIL_ADDRESS_OR_NUMBER)
            if (HambaUtils.isPhoneNumber(emailOrNumber)) {
                newPasswordRequest.number = emailOrNumber
            } else {
                newPasswordRequest.email = emailOrNumber
            }

            APiManager.resetPassword(this, this, newPasswordRequest)
        }
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is NewPasswordResponse) {
            if (apiResponse.success!!) {
                moveToSuccessMessageScreen()
            } else {
                AlertDialogProvider.showAlertDialog(this, AlertDialogProvider.DialogTheme.ThemeGreen, apiResponse.message)
            }
        }

        if (apiResponse is ResendOtpResponse) {
            if (apiResponse.success!!) {
                AlertDialogProvider.showAlertDialog(this, AlertDialogProvider.DialogTheme.ThemeGreen, apiResponse.message,
                    getString(R.string.verify), object : DialogButtonClickListener {
                    override fun onClick(alertDialog: AlertDialog) {
                        alertDialog.dismiss()
                    }
                })
            } else {
                AlertDialogProvider.showAlertDialog(this, AlertDialogProvider.DialogTheme.ThemeGreen, apiResponse.message)
            }
        }
    }

    private fun moveToSuccessMessageScreen() {
        val passwordResetIntent = Intent(this, PasswordResetStatusActivity::class.java)
        startActivity(passwordResetIntent)
    }

    fun onSendOtpAgainClicked(view: View) {

        val resendOtpRequest = ResendOtpRequest()

        val emailOrNumber = intent.getStringExtra(ForgetPasswordActivity.KEY_EMAIL_ADDRESS_OR_NUMBER)
        if (HambaUtils.isPhoneNumber(emailOrNumber)) {
            resendOtpRequest.number = emailOrNumber
            resendOtpRequest.userType = UserType.INDIVIDUAL.name
        } else {
            resendOtpRequest.email = emailOrNumber
            resendOtpRequest.userType = UserType.BUSINESS_EMPLOYEE.name
        }

        APiManager.resendOtpCode(this, this, resendOtpRequest)
    }

    private fun checkValidations(): Boolean {
        if (editText_otpCode.getText().length < 6) {
            editText_otpCode.setError(getString(R.string.please_enter_otp_code))
            return false
        }

        if (editText_newPassword.getText().isEmpty()) {
            editText_newPassword.setError(getString(R.string.please_enter_new_password))
            return false
        }

        if (editText_confirmPassword.getText().isEmpty()) {
            editText_confirmPassword.setError(getString(R.string.please_confirm_password))
            return false
        }

        if (!editText_confirmPassword.getText().contentEquals(editText_newPassword.getText())) {
            editText_confirmPassword.setError(getString(R.string.confirm_password_should_match_new_password))
            return false
        }

        return true
    }
}