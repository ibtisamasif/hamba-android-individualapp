package com.hadiftech.hamba.features.login

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
import com.hadiftech.hamba.core.services.HttpErrorCodes
import com.hadiftech.hamba.core.session.Session
import com.hadiftech.hamba.core.session.User
import com.hadiftech.hamba.features.dashboard.DashboardActivity
import com.hadiftech.hamba.features.forget_password.ForgetPasswordActivity
import com.hadiftech.hamba.features.login.login_service.LoginRequest
import com.hadiftech.hamba.features.login.login_service.LoginResponse
import com.hadiftech.hamba.features.profile.get_profile_service.GetProfileResponse
import com.hadiftech.hamba.features.signup.HelloUserActivity
import com.hadiftech.hamba.features.signup.IndividualSignupActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editText_password

class LoginActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onSignInButtonClicked(signInButton: View) {

        if (checkValidations()) {

            var loginRequest = LoginRequest()

            if (HambaUtils.isPhoneNumber(editText_emailOrNumber.getText())) {
                loginRequest.number = editText_emailOrNumber.getText()
                loginRequest.password = editText_password.getText()
            } else {
                loginRequest.email = editText_emailOrNumber.getText()
                loginRequest.password = editText_password.getText()
            }

            Session.clearSession()
            APiManager.loginApi(this, this, loginRequest)
        }
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is LoginResponse) {
            handleLoginResponse(apiResponse)
        }

        if (apiResponse is GetProfileResponse) {

            User.setCurrentProfileOutdated(false)
            User.saveUserProfile(apiResponse.details!!)
            
            if (User.getUserProfile()!!.firstName != null && User.getUserProfile()!!.firstName!!.isNotEmpty()) {
                moveToDashboardScreen()
            } else {
                moveToHelloScreen()
            }
        }
    }

    override fun onApiFailure(errorCode: Int) {
        if (errorCode == HttpErrorCodes.Unauthorized.code) {
            AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, getString(R.string.password_incorrect))
        } else {
            super.onApiFailure(errorCode)
        }
    }

    private fun handleLoginResponse(loginResponse: LoginResponse){
        if (loginResponse.status!!) {
            Session.storeSession(loginResponse.accessToken, loginResponse.secretKey, loginResponse.tokenType)
            callGetProfileApi()
        } else {
            AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, loginResponse.message)
        }
    }

    private fun callGetProfileApi(){
        if (Session.isSessionAvailable() && User.isCurrentProfileOutdated()) {
            APiManager.getUserProfile(this, this)
        } else {
            AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, getString(R.string.session_not_available))
        }
    }

    fun onCreateAccountClicked(createAccountTextView: View){
        val individualSignUpIntent = Intent(this, IndividualSignupActivity::class.java)
        startActivity(individualSignUpIntent)
    }

    fun onContinueAsGuestClicked(guestView: View) {
        val helloUserIntent = Intent(this, HelloUserActivity::class.java)
        startActivity(helloUserIntent)
    }

    fun onForgetPasswordClicked(forgetPasswordButton: View) {
        val forgetPasswordIntent = Intent(this, ForgetPasswordActivity::class.java)
        startActivity(forgetPasswordIntent)
    }

    private fun moveToHelloScreen() {
        val helloUserIntent = Intent(this, HelloUserActivity::class.java)
        startActivity(helloUserIntent)
        finish()
    }

    private fun moveToDashboardScreen() {
        val dashboardIntent = Intent(this, DashboardActivity::class.java)
        startActivity(dashboardIntent)
        finish()
    }

    private fun checkValidations() : Boolean {

        if (editText_emailOrNumber.getText().isEmpty()) {
            editText_emailOrNumber.setError(getString(R.string.please_enter_cell_number_or_email))
            return false
        }

        if (HambaUtils.isPhoneNumber(editText_emailOrNumber.getText())) {
            if (editText_emailOrNumber.getText().length < 10) {
                editText_emailOrNumber.setError(getString(R.string.please_enter_valid_cell_number))
                return false
            }
        } else {
            if (!HambaUtils.isEmailValid(editText_emailOrNumber.getText())) {
                editText_emailOrNumber.setError(getString(R.string.please_enter_valid_email))
                return false
            }
        }

        if (editText_password.getText().isEmpty()) {
            editText_password.setError(getString(R.string.please_enter_password))
            return false
        }

        return true
    }
}
