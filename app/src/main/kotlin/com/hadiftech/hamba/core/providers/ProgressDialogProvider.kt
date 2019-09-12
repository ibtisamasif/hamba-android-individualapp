package com.hadiftech.hamba.core.providers

import android.annotation.SuppressLint
import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD

object ProgressDialogProvider {

    @SuppressLint("StaticFieldLeak")
    private lateinit var progressHud: KProgressHUD

    fun initialize(context: Context) {

        progressHud = KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
    }

    fun show() {
        if (progressHud != null) { progressHud.show() }
    }

    fun dismiss() {
        if(progressHud != null) { progressHud.dismiss() }
    }
}