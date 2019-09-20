package com.hadiftech.hamba.core

object Constants {
    const val EMPTY_STRING = ""
    const val SPACE_STRING = " "
    const val LOGGER_TAG = "Hamba App"
    const val DOB_FORMAT = "MMM/dd/yyyy"
    const val NUMBER_REGULAR_EXPRESSION = "^[\\+\\d]?(?:[\\d-.\\s()]*)\$"
}

object Fonts {
    const val CO_HEADLINE_W01_BOLD = "fonts/co_headline_w01_bold.ttf"
    const val CO_HEADLINE_W01_LIGHT = "fonts/co_headline_w01_light.ttf"
    const val CO_HEADLINE_W01_REGULAR = "fonts/co_headline_w01_reg.ttf"
}

object EndPoints {
    const val API_LOGIN = "/api/auth/login"
    const val API_FORGET_PASSWORD = "/api/auth/forget_password"
    const val API_RESET_PASSWORD = "/api/auth/reset_password"
    const val API_SIGN_UP = "/api/auth/signup"
    const val API_VERIFY_OTP = "/api/auth/verify_otp"
    const val API_RESEND_OTP = "/api/auth/resend_otp"
    const val API_GET_PROFILE = "/api/auth/get_profile"
    const val API_EDIT_INDIVIDUAL_PROFILE = "/api/auth/edit_iprofile"
}

object Key {
    const val AUTHORIZATION = "Authorization"
}