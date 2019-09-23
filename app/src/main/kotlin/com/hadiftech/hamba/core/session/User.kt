package com.hadiftech.hamba.core.session

import android.content.Context
import android.content.SharedPreferences
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.enums.UserType

object User {

    private lateinit var userPreferences: SharedPreferences
    private const val userPreferenceName = "UserPreferences"

    fun initialize(context: Context){
        userPreferences = context.getSharedPreferences(userPreferenceName, Context.MODE_PRIVATE)
    }

    fun addUserName(userName: String?) {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.putString(UserConstants.Key_User_Name, userName)
        preferenceEditor.apply()
    }

    fun addUserType(userType: String?) {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.putString(UserConstants.Key_User_Type, userType)
        preferenceEditor.apply()
    }

    fun getUserName() : String {
        return userPreferences.getString(UserConstants.Key_User_Name, Constants.EMPTY_STRING)
    }

    fun getUserType() : String {
        return userPreferences.getString(UserConstants.Key_User_Type, Constants.EMPTY_STRING)
    }

    fun isGuestUser() : Boolean {
        return userPreferences.getString(UserConstants.Key_User_Type, Constants.EMPTY_STRING) == UserType.GUEST.name
    }

    fun wipeUserData() {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.remove(UserConstants.Key_User_Name)
        preferenceEditor.remove(UserConstants.Key_User_Type)
        preferenceEditor.apply()
    }
}