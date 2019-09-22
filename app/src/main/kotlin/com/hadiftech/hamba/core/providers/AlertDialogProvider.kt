package com.hadiftech.hamba.core.providers

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.hadiftech.hamba.R
import androidx.core.content.ContextCompat.getSystemService
import com.hadiftech.hamba.core.views.HambaTextView


object AlertDialogProvider {

    fun showFailureDialog(context: Context){
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setTitle(context.getString(R.string.error))
        dialogBuilder.setMessage(context.getString(R.string.sorry_something_went_wrong))
        dialogBuilder.setPositiveButton(android.R.string.yes) { dialog, which -> }
        dialogBuilder.show()
    }

    fun showAlertDialog2(context: Context, message: String?) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setTitle(context.getString(R.string.alert))
        dialogBuilder.setMessage(message)
        dialogBuilder.setPositiveButton(android.R.string.yes) { dialog, which -> }
        dialogBuilder.show()
    }

    fun showAlertDialog(context: Context, message: String?) {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutView = inflater.inflate(R.layout.dialog_postive_layout, null)
        val textView_alertMessage = layoutView.findViewById<HambaTextView>(R.id.textView_alertMessage)
        textView_alertMessage.text = message
        val dialogButton = layoutView.findViewById<Button>(R.id.btnDialog)
        dialogBuilder.setView(layoutView)
        val alertDialog = dialogBuilder.create()
        alertDialog.window.attributes.windowAnimations = R.style.DialogAnimation
        alertDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        dialogButton.setOnClickListener { alertDialog.dismiss() }
    }

    fun showAlertDialog(context: Context, message: String?, yesBtnText: String?, yesBtnClickListener: DialogInterface.OnClickListener) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setTitle(context.getString(R.string.alert))
        dialogBuilder.setMessage(message)
        dialogBuilder.setPositiveButton(yesBtnText, yesBtnClickListener)
        dialogBuilder.show()
    }
}