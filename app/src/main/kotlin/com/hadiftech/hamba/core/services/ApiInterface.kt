package com.hadiftech.hamba.core.services

import com.hadiftech.hamba.core.EndPoints
import com.hadiftech.hamba.core.Key
import com.hadiftech.hamba.features.forget_password.forget_password_service.ForgetPasswordRequest
import com.hadiftech.hamba.features.forget_password.forget_password_service.ForgetPasswordResponse
import com.hadiftech.hamba.features.forget_password.new_password_service.NewPasswordRequest
import com.hadiftech.hamba.features.forget_password.new_password_service.NewPasswordResponse
import com.hadiftech.hamba.features.login.login_service.LoginRequest
import com.hadiftech.hamba.features.login.login_service.LoginResponse
import com.hadiftech.hamba.features.profile.get_profile_service.GetProfileResponse
import com.hadiftech.hamba.features.signup.code_verification_service.VerifyOtpRequest
import com.hadiftech.hamba.features.signup.code_verification_service.VerifyOtpResponse
import com.hadiftech.hamba.features.signup.resend_otp_service.ResendOtpRequest
import com.hadiftech.hamba.features.signup.resend_otp_service.ResendOtpResponse
import com.hadiftech.hamba.features.signup.sign_up_service.SignUpRequest
import com.hadiftech.hamba.features.signup.sign_up_service.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST(EndPoints.API_LOGIN)
    fun loginToHamba(@Body loginRequest: LoginRequest) : Call<LoginResponse>

    @POST(EndPoints.API_FORGET_PASSWORD)
    fun forgetPassword(@Body forgetPasswordRequest: ForgetPasswordRequest) : Call<ForgetPasswordResponse>

    @POST(EndPoints.API_RESET_PASSWORD)
    fun resetPassword(@Body newPasswordRequest: NewPasswordRequest) : Call<NewPasswordResponse>

    @POST(EndPoints.API_SIGN_UP)
    fun signUpToHamba(@Body signUpRequest: SignUpRequest) : Call<SignUpResponse>

    @POST(EndPoints.API_VERIFY_OTP)
    fun verifyOtpCode(@Body verifyOtpRequest: VerifyOtpRequest) : Call<VerifyOtpResponse>

    @POST(EndPoints.API_RESEND_OTP)
    fun resendOtpCode(@Body resendOtpRequest: ResendOtpRequest) : Call<ResendOtpResponse>

    @GET(EndPoints.API_GET_PROFILE)
    fun getUserProfile(@Header(Key.AUTHORIZATION) accessToken: String) : Call<GetProfileResponse>
}