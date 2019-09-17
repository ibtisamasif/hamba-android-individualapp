package com.hadiftech.hamba.features.signup.sign_up_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.services.HambaBaseApiResponse

class SignUpResponse : HambaBaseApiResponse() {

    @SerializedName("verified_by_email_or_phoneNumber")
    var accountVerificationStatus: Boolean? = false

    @SerializedName("success")
    var success: Boolean? = false

    @SerializedName("message")
    var message: String? = Constants.EMPTY_STRING
}