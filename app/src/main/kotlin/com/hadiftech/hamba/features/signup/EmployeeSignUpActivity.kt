package com.hadiftech.hamba.features.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.core.HambaUtils
import com.hadiftech.hamba.features.signup.sign_up_service.EmployeeSignUpRequest
import com.hadiftech.hamba.core.enums.UserType
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.features.signup.sign_up_service.SignUpResponse
import kotlinx.android.synthetic.main.activity_business_sign_up.*

class EmployeeSignUpActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_sign_up)

        editText_password.showPasswordVisibilityToggle(true)
    }

    fun onSignUpButtonClicked(signUpButton: View) {

        if (checkValidations()) {
            var employeeSignUpRequest = EmployeeSignUpRequest()
            employeeSignUpRequest.preSharedKey = editText_preSharedKey.getText()
            employeeSignUpRequest.firstName = editText_firstName.getText()
            employeeSignUpRequest.lastName = editText_lastName.getText()
            employeeSignUpRequest.storeName = editText_businessName.getText()
            employeeSignUpRequest.email = editText_emailAddress.getText()
            employeeSignUpRequest.number = businessSigUp_mobileNumberComponent.getPhoneNumberWithPrefix()
            employeeSignUpRequest.password = editText_password.getText()
            employeeSignUpRequest.type = UserType.BUSINESS_EMPLOYEE.name

            APiManager.signUpApi(this, this, employeeSignUpRequest)
        }
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is SignUpResponse) {
            if (apiResponse.success!!) {
                moveToCodeVerificationScreen()
            } else {
                AlertDialogProvider.showAlertDialog(this, AlertDialogProvider.DialogTheme.ThemeWhite, apiResponse.message)
            }
        }
    }

    private fun moveToCodeVerificationScreen() {
        val codeVerificationIntent = Intent(this, CodeVerificationActivity::class.java)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_TYPE, UserType.BUSINESS_EMPLOYEE)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_EMAIL, editText_emailAddress.getText())
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_NUMBER, businessSigUp_mobileNumberComponent.getPhoneNumberWithPrefix())
        startActivity(codeVerificationIntent)
    }

    private fun checkValidations() : Boolean {

        if (editText_preSharedKey.getText().isEmpty()) {
            editText_preSharedKey.setError(getString(R.string.please_enter_pre_shared_key))
            return false
        }

        if (editText_firstName.getText().isEmpty()) {
            editText_firstName.setError(getString(R.string.please_enter_first_name))
            return false
        }

        if (editText_lastName.getText().isEmpty()) {
            editText_lastName.setError(getString(R.string.please_enter_last_name))
            return false
        }

        if (editText_businessName.getText().isEmpty()) {
            editText_businessName.setError(getString(R.string.please_enter_business_name))
            return false
        }

        if (editText_emailAddress.getText().isEmpty()) {
            editText_emailAddress.setError(getString(R.string.please_enter_email))
            return false
        }

        if (!HambaUtils.isEmailValid(editText_emailAddress.getText())) {
            editText_emailAddress.setError(getString(R.string.please_enter_valid_email))
            return false
        }

        if (businessSigUp_mobileNumberComponent.getPhoneNumber().length < 10) {
            businessSigUp_mobileNumberComponent.setError(getString(R.string.please_enter_valid_cell_number))
        }

        if (editText_password.getText().isEmpty()) {
            editText_password.setError(getString(R.string.please_enter_password))
            return false
        }

        return true
    }
}