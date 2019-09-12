package com.hadiftech.hamba.features.login

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.services.HambaBaseApiResponse

class LoginResponse : HambaBaseApiResponse() {

    @SerializedName("status")
    var status: Boolean? = false

    @SerializedName("success")
    var success: Boolean? = false

    @SerializedName("message")
    var message: String? = Constants.EMPTY_STRING

    @SerializedName("accessToken")
    var accessToken: String? = Constants.EMPTY_STRING

    @SerializedName("tokenType")
    var tokenType: String? = Constants.EMPTY_STRING

    @SerializedName("secretKey")
    var secretKey: String? = Constants.EMPTY_STRING
}