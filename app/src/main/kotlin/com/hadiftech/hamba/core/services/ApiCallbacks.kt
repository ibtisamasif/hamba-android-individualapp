package com.hadiftech.hamba.core.services

interface ApiCallbacks {

    fun doBeforeApiCall()

    fun doAfterApiCall()

    fun onApiFailure(errorCode: Int)

    fun onApiSuccess(apiResponse: HambaBaseApiResponse)
}