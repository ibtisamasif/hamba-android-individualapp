package com.hadiftech.hamba.core.services

import android.content.Context
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaUtils
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiExecutor<T> : Callback<T> {

    private lateinit var mApiCallbacks: ApiCallbacks

    override fun onResponse(call: Call<T>, response: Response<T>) {

        mApiCallbacks.doAfterApiCall()

        if (response.isSuccessful){
            var apiResponse = response.body() as HambaBaseApiResponse
            apiResponse.statusCode = response.code()
            apiResponse.statusMessage = response.message()
            mApiCallbacks.onApiSuccess(apiResponse)
        } else {
            mApiCallbacks.onApiFailure(response.code())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        mApiCallbacks.doAfterApiCall()
        mApiCallbacks.onApiFailure(HttpErrorCodes.Unknown.code)
    }

    fun addCallToQueue(context: Context, apiCall: Call<T>, apiCallbacks: ApiCallbacks) {
        if (HambaUtils.isNetworkAvailable(context)){
            this.mApiCallbacks = apiCallbacks
            apiCallbacks.doBeforeApiCall()
            apiCall.enqueue(this)
        } else {
            AlertDialogProvider.showAlertDialog(context, AlertDialogProvider.DialogTheme.ThemeWhite, context.getString(R.string.no_network_available))
        }
    }
}