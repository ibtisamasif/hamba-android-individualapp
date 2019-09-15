package com.hadiftech.hamba.core.services

import android.content.Context
import com.hadiftech.hamba.features.forget_password.forget_password_service.ForgetPasswordRequest
import com.hadiftech.hamba.features.forget_password.forget_password_service.ForgetPasswordResponse
import com.hadiftech.hamba.features.forget_password.new_password_service.NewPasswordRequest
import com.hadiftech.hamba.features.forget_password.new_password_service.NewPasswordResponse
import com.hadiftech.hamba.features.login.login_service.LoginRequest
import com.hadiftech.hamba.features.login.login_service.LoginResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APiManager {

    private lateinit var hambaServices: ApiInterface
    private const val BASE_URL = "http://167.71.241.65:8080"

    fun initialize(){
        hambaServices = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    fun loginApi(context: Context, apiCallbacks: ApiCallbacks, loginRequest: LoginRequest) {
        val loginApiCall = hambaServices.loginToHamba(loginRequest)
        ApiExecutor<LoginResponse>().addCallToQueue(context, loginApiCall, apiCallbacks)
    }

    fun forgetPassword(context: Context, apiCallbacks: ApiCallbacks, forgetPasswordRequest: ForgetPasswordRequest) {
        val forgetPasswordApiCall = hambaServices.forgetPassword(forgetPasswordRequest)
        ApiExecutor<ForgetPasswordResponse>().addCallToQueue(context, forgetPasswordApiCall, apiCallbacks)
    }

    fun resetPassword(context: Context, apiCallbacks: ApiCallbacks, newPasswordRequest: NewPasswordRequest) {
        val newPasswordApiCall = hambaServices.resetPassword(newPasswordRequest)
        ApiExecutor<NewPasswordResponse>().addCallToQueue(context, newPasswordApiCall, apiCallbacks)
    }
}