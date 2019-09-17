package com.hadiftech.hamba.features.login.login_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants

class LoginRequest {

    @SerializedName("email")
    var email: String? = Constants.EMPTY_STRING

    @SerializedName("number")
    var number: String? = Constants.EMPTY_STRING

    @SerializedName("password")
    var password: String? = Constants.EMPTY_STRING
}