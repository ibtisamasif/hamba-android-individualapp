package com.hadiftech.hamba.core.services

import android.content.Context
import com.hadiftech.hamba.features.login.LoginRequest
import com.hadiftech.hamba.features.login.LoginResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APiManager {

    private lateinit var retrofit: Retrofit
    private val BASE_URL = "http://167.71.241.65:8080"

    fun initialize(){
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun loginApi(context: Context, apiCallbacks: ApiCallbacks, loginRequest: LoginRequest) {
        val hambaServices = retrofit.create(ApiInterface::class.java)
        val loginApiCall = hambaServices.loginToHamba(loginRequest)
        ApiExecutor<LoginResponse>().addCallToQueue(context, loginApiCall, apiCallbacks)
    }
}