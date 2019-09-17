package com.hadiftech.hamba.features.signup.code_verification_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants

class VerifyBusinessOtpRequest : VerifyOtpRequest() {

    @SerializedName("otp_code_email")
    var otpCodeEmail: String? = Constants.EMPTY_STRING
}