package com.hadiftech.hamba.features.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.core.enums.UserType
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.features.signup.sign_up_service.SignUpRequest
import com.hadiftech.hamba.features.signup.sign_up_service.SignUpResponse
import kotlinx.android.synthetic.main.activity_individual_signup.*

class IndividualSignupActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_signup)
    }

    fun onSignUpButtonClicked(signUpButton: View) {

        if (checkValidations()) {
            var individualSignUpRequest = SignUpRequest()
            individualSignUpRequest.number = individualSignUp_mobileNumberComponent.getPhoneNumberWithPrefix()
            individualSignUpRequest.type = UserType.INDIVIDUAL.name

            APiManager.signUpApi(this, this, individualSignUpRequest)
        }
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is SignUpResponse) {
            if (apiResponse.success!!){
                moveToCodeVerificationActivity()
            } else {
                AlertDialogProvider.showAlertDialog(this, AlertDialogProvider.DialogTheme.ThemeWhite, apiResponse.message)
            }
        }
    }

    private fun checkValidations() : Boolean {
        if (individualSignUp_mobileNumberComponent.getPhoneNumber().length < 10) {
            individualSignUp_mobileNumberComponent.setError(getString(R.string.please_enter_valid_cell_number))
            return false
        }

        return true
    }

    private fun moveToCodeVerificationActivity() {
        val codeVerificationIntent = Intent(this, CodeVerificationActivity::class.java)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_TYPE, UserType.INDIVIDUAL)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_EMAIL, Constants.EMPTY_STRING)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_NUMBER, individualSignUp_mobileNumberComponent.getPhoneNumberWithPrefix())
        startActivity(codeVerificationIntent)
    }
}