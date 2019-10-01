package com.hadiftech.hamba.core.session

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.enums.UserType
import com.hadiftech.hamba.features.profile.get_profile_service.GetProfileResponse

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

    fun saveUserProfile(userProfile: GetProfileResponse.Details) {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.putString(UserConstants.Key_User_Profile, Gson().toJson(userProfile))
        preferenceEditor.apply()
        setCurrentProfileOutdated(false)
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

    fun getUserProfile() : GetProfileResponse.Details? {
        var userProfile = userPreferences.getString(UserConstants.Key_User_Profile, Constants.EMPTY_STRING)
        if (userProfile.isEmpty()) {
            return GetProfileResponse.Details()
        } else {
            return Gson().fromJson<GetProfileResponse.Details>(userProfile, GetProfileResponse.Details::class.java)
        }
    }

    fun getProfileUpdatePercentage() : String {

        var updatePercentage = 30 //Default percentage is 30
        var userProfile = getUserProfile()

        if (userProfile!!.avatar != null && userProfile.avatar!!.isNotEmpty()) {
            updatePercentage += 20
        }

        if ((userProfile!!.firstName != null && userProfile.firstName!!.isNotEmpty())
            && userProfile!!.lastName != null && userProfile.lastName!!.isNotEmpty()
            && userProfile!!.personType != null && userProfile.personType!!.isNotEmpty()
            && userProfile!!.prefix != null && userProfile.prefix!!.isNotEmpty()
            && userProfile!!.gender != null && userProfile.gender!!.isNotEmpty()
            && userProfile!!.birthDate != null && userProfile.birthDate!!.isNotEmpty()
            && userProfile!!.nationality != null && userProfile.nationality!!.isNotEmpty()
            && userProfile!!.registrationType != null && userProfile.registrationType!!.isNotEmpty()
            && userProfile!!.registrationNo != null && userProfile.registrationNo!!.isNotEmpty()
            && userProfile!!.country != null && userProfile.country!!.isNotEmpty()
            && userProfile!!.cityName != null && userProfile.cityName!!.isNotEmpty()
            && userProfile!!.addressType != null && userProfile.addressType!!.isNotEmpty()
            && userProfile!!.address != null && userProfile.address!!.isNotEmpty()) {

            updatePercentage += 50
        }

        return "$updatePercentage%"
    }

    fun setCurrentProfileOutdated(status: Boolean) {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.putBoolean(UserConstants.Key_Profile_Status, status)
        preferenceEditor.apply()
    }

    fun isCurrentProfileOutdated() : Boolean {
        return userPreferences.getBoolean(UserConstants.Key_Profile_Status, true)
    }

    fun isGuestUser() : Boolean {
        return userPreferences.getString(UserConstants.Key_User_Type, Constants.EMPTY_STRING) == UserType.GUEST.name
    }

    fun wipeUserData() {
        val preferenceEditor = userPreferences.edit()
        preferenceEditor.remove(UserConstants.Key_User_Name)
        preferenceEditor.remove(UserConstants.Key_User_Type)
        preferenceEditor.remove(UserConstants.Key_User_Profile)
        preferenceEditor.remove(UserConstants.Key_Profile_Status)
        preferenceEditor.apply()
    }
}