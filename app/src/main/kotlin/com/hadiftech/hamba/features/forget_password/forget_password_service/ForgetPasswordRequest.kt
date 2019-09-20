package com.hadiftech.hamba.features.forget_password.forget_password_service

import com.google.gson.annotations.SerializedName

class ForgetPasswordRequest {

    @SerializedName("email")
    var email: String? = null

    @SerializedName("number")
    var number: String? = null
}