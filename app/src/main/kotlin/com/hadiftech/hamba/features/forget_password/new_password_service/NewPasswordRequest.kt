package com.hadiftech.hamba.features.forget_password.new_password_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants

class NewPasswordRequest {

    @SerializedName("email")
    var email: String? = Constants.EMPTY_STRING

    @SerializedName("number")
    var number: String? = Constants.EMPTY_STRING

    @SerializedName("otp_code")
    var otpCode: String? = Constants.EMPTY_STRING

    @SerializedName("new_password")
    var newPassword: String? = Constants.EMPTY_STRING
}