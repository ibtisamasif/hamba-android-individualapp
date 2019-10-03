package com.hadiftech.hamba.core.providers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.enums.DialogTheme
import com.hadiftech.hamba.core.listeners.DialogButtonClickListener
import com.hadiftech.hamba.core.views.HambaButton
import com.hadiftech.hamba.core.views.HambaTextView

@SuppressLint("StaticFieldLeak")
object AlertDialogProvider {

    private var negativeButton: TextView? = null
    private var positiveButton: HambaButton? = null
    private var textViewTitle: HambaTextView? = null
    private var textViewMessage: HambaTextView? = null

    fun showFailureDialog(context: Context, theme: DialogTheme){
        val dialogView = getView(context, theme)
        initializeViews(dialogView)
        textViewTitle!!.text = context.getString(R.string.error)
        textViewMessage!!.text = context.getString(R.string.sorry_something_went_wrong)
        val alertDialog = createAlertDialog(context, dialogView)
        positiveButton!!.setOnClickListener { alertDialog.dismiss() }
    }

    fun showAlertDialog(context: Context, theme: DialogTheme, message: String?) {
        val dialogView = getView(context, theme)
        initializeViews(dialogView)
        textViewMessage!!.text = message
        val alertDialog = createAlertDialog(context, dialogView)
        positiveButton!!.setOnClickListener { alertDialog.dismiss() }
    }

    fun showAlertDialog(context: Context, theme: DialogTheme, message: String?, positiveBtnText: String?, dialogButtonClickListener: DialogButtonClickListener) {
        val dialogView = getView(context, theme)
        initializeViews(dialogView)
        textViewMessage!!.text = message
        positiveButton!!.text = positiveBtnText
        val alertDialog = createAlertDialog(context, dialogView)
        positiveButton!!.setOnClickListener { dialogButtonClickListener.onClick(alertDialog) }
    }

    fun showAlertDialog(context: Context, theme: DialogTheme, message: String?, positiveBtnText: String?, positiveClickListener: DialogButtonClickListener,
                        negativeClickListener: DialogButtonClickListener) {
        val dialogView = getView(context, theme)
        initializeViews(dialogView)
        textViewMessage!!.text = message
        positiveButton!!.text = positiveBtnText
        negativeButton!!.visibility = View.VISIBLE
        val alertDialog = createAlertDialog(context, dialogView)
        positiveButton!!.setOnClickListener { positiveClickListener.onClick(alertDialog) }
        negativeButton!!.setOnClickListener { negativeClickListener.onClick(alertDialog) }
    }

    private fun getView(context: Context, theme: DialogTheme) : View {
        var layoutView: View? = null
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        when (theme) {
            DialogTheme.ThemeGreen -> layoutView = inflater.inflate(R.layout.layout_alert_dialog_green, null)
            DialogTheme.ThemeWhite -> layoutView = inflater.inflate(R.layout.layout_alert_dialog_white, null)
        }

        return layoutView
    }

    private fun initializeViews(dialogView: View) {
        negativeButton = dialogView.findViewById<TextView>(R.id.negativeBtn)
        positiveButton = dialogView.findViewById<HambaButton>(R.id.positiveBtn)
        textViewTitle = dialogView.findViewById<HambaTextView>(R.id.textView_title)
        textViewMessage = dialogView.findViewById<HambaTextView>(R.id.textView_message)
    }

    private fun createAlertDialog(context: Context, dialogView: View) : AlertDialog {
        val alertDialog = AlertDialog.Builder(context).setView(dialogView).create()
        alertDialog.window.attributes.windowAnimations = R.style.DialogAnimation
        alertDialog.setCancelable(false)
        alertDialog.show()

        alertDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return alertDialog
    }
}