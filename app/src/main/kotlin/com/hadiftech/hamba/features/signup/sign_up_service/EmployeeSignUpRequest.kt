package com.hadiftech.hamba.features.signup.sign_up_service

import com.google.gson.annotations.SerializedName
import com.hadiftech.hamba.core.Constants

class EmployeeSignUpRequest : SignUpRequest() {

    @SerializedName("firstName")
    var firstName: String? = Constants.EMPTY_STRING

    @SerializedName("lastName")
    var lastName: String? = Constants.EMPTY_STRING

    @SerializedName("password")
    var password: String? = Constants.EMPTY_STRING

    @SerializedName("preSharedKey")
    var preSharedKey: String? = Constants.EMPTY_STRING

    @SerializedName("storeName")
    var storeName: String? = Constants.EMPTY_STRING
}