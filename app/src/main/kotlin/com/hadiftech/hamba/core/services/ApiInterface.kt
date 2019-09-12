package com.hadiftech.hamba.core.services

import com.hadiftech.hamba.core.EndPoints
import com.hadiftech.hamba.features.login.LoginRequest
import com.hadiftech.hamba.features.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST(EndPoints.LOGIN_API)
    fun loginToHamba(@Body loginRequest: LoginRequest) : Call<LoginResponse>
}