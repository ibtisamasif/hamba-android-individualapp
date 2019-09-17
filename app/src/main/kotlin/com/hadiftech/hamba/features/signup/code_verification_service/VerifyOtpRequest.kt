package com.hadiftech.hamba.features.signup.code_verification_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants

open class VerifyOtpRequest {

    @SerializedName("otp_code")
    var otpCode: String? = Constants.EMPTY_STRING

    @SerializedName("mode")
    var userType: String? = Constants.EMPTY_STRING
}