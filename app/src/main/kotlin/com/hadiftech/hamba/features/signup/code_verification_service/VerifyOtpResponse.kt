package com.hadiftech.hamba.features.signup.code_verification_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.services.HambaBaseApiResponse

class VerifyOtpResponse : HambaBaseApiResponse() {

    @SerializedName("success")
    var success: Boolean? = false

    @SerializedName("message")
    var message: String? = Constants.EMPTY_STRING
}