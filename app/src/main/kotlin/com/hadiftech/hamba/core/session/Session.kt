package com.hadiftech.hamba.core.session

import android.content.Context
import android.content.SharedPreferences
import com.hadiftech.hamba.core.Constants

object Session {

    private lateinit var sessionPreferences: SharedPreferences
    private const val sessionPreferenceName = "SessionPreferences"

    fun initialize(context: Context){
        sessionPreferences = context.getSharedPreferences(sessionPreferenceName, Context.MODE_PRIVATE)
    }

    fun storeSession(accessToken: String?, secretKey: String?, tokenType: String?) {
        val preferenceEditor = sessionPreferences.edit()
        preferenceEditor.putString(PreferenceConstants.Key_SecretKey, secretKey)
        preferenceEditor.putString(PreferenceConstants.Key_TokenType, tokenType)
        preferenceEditor.putString(PreferenceConstants.Key_AccessToken, tokenType + Constants.SPACE_STRING + accessToken)
        preferenceEditor.apply()
    }

    fun isSessionAvailable() : Boolean {
        return sessionPreferences.getString(PreferenceConstants.Key_AccessToken, Constants.EMPTY_STRING).isNotEmpty()
    }

    fun getAccessToken() : String {
        return sessionPreferences.getString(PreferenceConstants.Key_AccessToken, Constants.EMPTY_STRING)
    }

    fun getSecretKey() : String {
        return sessionPreferences.getString(PreferenceConstants.Key_SecretKey, Constants.EMPTY_STRING)
    }

    fun getTokenType() : String {
        return sessionPreferences.getString(PreferenceConstants.Key_TokenType, Constants.EMPTY_STRING)
    }

    fun clearSession() {
        val preferenceEditor = sessionPreferences.edit()
        preferenceEditor.remove(PreferenceConstants.Key_AccessToken)
        preferenceEditor.remove(PreferenceConstants.Key_SecretKey)
        preferenceEditor.remove(PreferenceConstants.Key_TokenType)
        preferenceEditor.apply()
    }
}