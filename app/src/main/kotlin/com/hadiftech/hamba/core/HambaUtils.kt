package com.hadiftech.hamba.core

import android.content.Context
import android.net.ConnectivityManager
import android.util.Patterns

object HambaUtils {

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPhoneNumber(string: String) : Boolean {
        return string.matches(Constants.NUMBER_REGULAR_EXPRESSION.toRegex())
    }

    fun isNetworkAvailable (context: Context) : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return  networkInfo != null && networkInfo.isConnected
    }
}