package com.hadiftech.hamba.features.forget_password.forget_password_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.services.HambaBaseApiResponse

class ForgetPasswordResponse : HambaBaseApiResponse() {

    @SerializedName("success")
    var success: Boolean? = false

    @SerializedName("message")
    var message: String? = Constants.EMPTY_STRING
}