package com.hadiftech.hamba.features.signup.resend_otp_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants

class ResendOtpRequest {

    @SerializedName("email")
    var email: String? = Constants.EMPTY_STRING

    @SerializedName("number")
    var number: String? = Constants.EMPTY_STRING

    @SerializedName("mode")
    var userType: String? = Constants.EMPTY_STRING
}