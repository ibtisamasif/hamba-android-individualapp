package com.hadiftech.hamba.features.forget_password.forget_password_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants

class ForgetPasswordRequest {

    @SerializedName("email")
    var email: String? = Constants.EMPTY_STRING

    @SerializedName("number")
    var number: String? = Constants.EMPTY_STRING
}