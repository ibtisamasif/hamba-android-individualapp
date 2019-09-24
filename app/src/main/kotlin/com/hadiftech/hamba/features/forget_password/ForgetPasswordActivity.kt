package com.hadiftech.hamba.features.forget_password

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.core.HambaUtils
import com.hadiftech.hamba.core.enums.DialogTheme
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.features.forget_password.forget_password_service.ForgetPasswordRequest
import com.hadiftech.hamba.features.forget_password.forget_password_service.ForgetPasswordResponse
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : HambaBaseActivity() {

    companion object {
        @JvmStatic val KEY_EMAIL_ADDRESS_OR_NUMBER = "EmailAddressOrNumber"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
    }

    fun onContinueButtonClicked(continueButton: View) {

        if (checkValidations()) {

            var forgetPasswordRequest = ForgetPasswordRequest()
            if (HambaUtils.isPhoneNumber(editText_emailAddress.getText())) {
                forgetPasswordRequest.number = editText_emailAddress.getText()
            } else {
                forgetPasswordRequest.email = editText_emailAddress.getText()
            }

            APiManager.forgetPassword(this, this, forgetPasswordRequest)
        }
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is ForgetPasswordResponse) {
            if (apiResponse.success!!) {
                moveToNewPasswordActivity()
            } else {
                AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeGreen, apiResponse.message)
            }
        }
    }

    private fun moveToNewPasswordActivity(){
        val newPasswordIntent = Intent(this, NewPasswordActivity::class.java)
        newPasswordIntent.putExtra(KEY_EMAIL_ADDRESS_OR_NUMBER, editText_emailAddress.getText())
        startActivity(newPasswordIntent)
    }

    private fun checkValidations() : Boolean {

        if (editText_emailAddress.getText().isEmpty()) {
            editText_emailAddress.setError(getString(R.string.please_enter_cell_number_or_email))
            return false
        }

        if (HambaUtils.isPhoneNumber(editText_emailAddress.getText())) {
            if (editText_emailAddress.getText().length < 10) {
                editText_emailAddress.setError(getString(R.string.please_enter_valid_cell_number))
                return false
            }
        } else {
            if (!HambaUtils.isEmailValid(editText_emailAddress.getText())) {
                editText_emailAddress.setError(getString(R.string.please_enter_valid_email))
                return false
            }
        }

        return true
    }
}