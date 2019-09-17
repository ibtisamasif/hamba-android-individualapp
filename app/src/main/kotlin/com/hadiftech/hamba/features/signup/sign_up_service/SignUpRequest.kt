package com.hadiftech.hamba.features.signup.sign_up_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants

open class SignUpRequest {

    @SerializedName("number")
    var number: String? = Constants.EMPTY_STRING

    @SerializedName("type")
    var type: String? = Constants.EMPTY_STRING
}