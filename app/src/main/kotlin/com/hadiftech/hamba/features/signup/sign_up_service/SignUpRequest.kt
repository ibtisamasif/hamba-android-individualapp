package com.hadiftech.hamba.features.signup.sign_up_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants

open class SignUpRequest {

    @SerializedName("email")
    var email: String? = null

    @SerializedName("number")
    var number: String? = null

    @SerializedName("type")
    var type: String? = Constants.EMPTY_STRING
}