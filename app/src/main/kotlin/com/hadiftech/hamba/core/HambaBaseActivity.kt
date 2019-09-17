package com.hadiftech.hamba.core

import androidx.appcompat.app.AppCompatActivity
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.providers.ProgressDialogProvider
import com.hadiftech.hamba.core.services.ApiCallbacks
import com.hadiftech.hamba.core.services.HambaBaseApiResponse

abstract class HambaBaseActivity : AppCompatActivity(), ApiCallbacks {

    override fun doBeforeApiCall() {
        ProgressDialogProvider.show(this)
    }

    override fun doAfterApiCall() {
        ProgressDialogProvider.dismiss()
    }

    override fun onApiFailure(errorCode: Int) {
        AlertDialogProvider.showFailureDialog(this)
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {}
}