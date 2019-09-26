package com.hadiftech.hamba.features.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.core.enums.DialogTheme
import com.hadiftech.hamba.core.enums.UserType
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.features.signup.sign_up_service.SignUpRequest
import com.hadiftech.hamba.features.signup.sign_up_service.SignUpResponse
import kotlinx.android.synthetic.main.activity_individual_signup.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

class IndividualSignupActivity : HambaBaseActivity() {

    private val RC_SIGN_IN = 1111
    private var gmailId: String? = Constants.EMPTY_STRING
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_signup)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    fun onSignUpButtonClicked(signUpButton: View) {
        gmailId = Constants.EMPTY_STRING
        signUpUsingNumber()
    }

    private fun signUpUsingNumber() {
        if (checkValidations()) {
            var individualSignUpRequest = SignUpRequest()
            individualSignUpRequest.number = individualSignUp_mobileNumberComponent.getPhoneNumberWithPrefix()
            individualSignUpRequest.type = UserType.INDIVIDUAL.name

            APiManager.signUpApi(this, this, individualSignUpRequest)
        }
    }

    private fun signUpUsingGmailId() {
        var individualSignUpRequest = SignUpRequest()
        individualSignUpRequest.email = gmailId
        individualSignUpRequest.type = UserType.INDIVIDUAL.name

        APiManager.signUpApi(this, this, individualSignUpRequest)
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is SignUpResponse) {
            if (apiResponse.success!!){
                if (gmailId!!.isEmpty()) {
                    verifyCodeSentOnNumber()
                } else {
                    verifyCodeSentOnEmail()
                }
            } else {
                AlertDialogProvider.showAlertDialog(this, DialogTheme.ThemeWhite, apiResponse.message)
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

    fun onSignUpWithGmailClicked(view: View) {
        startActivityForResult(mGoogleSignInClient.signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            handleGoogleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            gmailId = account!!.email
            signUpUsingGmailId()
        } catch (e: ApiException) {
            AlertDialogProvider.showFailureDialog(this, DialogTheme.ThemeWhite)
        }
    }

    private fun verifyCodeSentOnNumber() {
        val codeVerificationIntent = Intent(this, CodeVerificationActivity::class.java)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_TYPE, UserType.INDIVIDUAL)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_EMAIL, Constants.EMPTY_STRING)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_NUMBER, individualSignUp_mobileNumberComponent.getPhoneNumberWithPrefix())
        startActivity(codeVerificationIntent)
    }

    private fun verifyCodeSentOnEmail() {
        val codeVerificationIntent = Intent(this, CodeVerificationActivity::class.java)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_TYPE, UserType.INDIVIDUAL)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_EMAIL, gmailId)
        codeVerificationIntent.putExtra(CodeVerificationActivity.KEY_USER_NUMBER, Constants.EMPTY_STRING)
        startActivity(codeVerificationIntent)
    }
}