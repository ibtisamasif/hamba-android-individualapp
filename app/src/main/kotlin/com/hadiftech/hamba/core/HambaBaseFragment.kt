package com.hadiftech.hamba.core

import androidx.fragment.app.Fragment
import com.hadiftech.hamba.core.enums.DialogTheme
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.providers.ProgressDialogProvider
import com.hadiftech.hamba.core.services.ApiCallbacks
import com.hadiftech.hamba.core.services.HambaBaseApiResponse

abstract class HambaBaseFragment : Fragment(), ApiCallbacks {

    override fun doBeforeApiCall() {
        ProgressDialogProvider.show(activity!!)
    }

    override fun doAfterApiCall() {
        ProgressDialogProvider.dismiss()
    }

    override fun onApiFailure(errorCode: Int) {
        AlertDialogProvider.showFailureDialog(activity!!, DialogTheme.ThemeWhite)
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {}
}