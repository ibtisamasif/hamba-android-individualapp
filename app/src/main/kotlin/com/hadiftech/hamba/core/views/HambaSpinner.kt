package com.hadiftech.hamba.core.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import androidx.appcompat.widget.AppCompatSpinner
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.providers.TypefaceProvider

class HambaSpinner : AppCompatSpinner, TypefaceProvider {
    var layout =  R.layout.spinner_item_light

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HambaLoginSpinnerAttributes, 0, 0)
        when (typedArray.getInteger(R.styleable.HambaLoginSpinnerAttributes_spinnerTheme, 0)) {
            0 -> setThemeWhite()
            1 -> setThemeGreen()
        }
    }

    fun populate(context: Context, spinnerItems: List<String>) {
        val adapter = ArrayAdapter(context, layout, spinnerItems)
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        this.adapter = adapter
    }

    fun populate(context: Context, @ArrayRes textArrayResId: Int) {
        val adapter = ArrayAdapter.createFromResource(context, textArrayResId, layout)
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        this.adapter = adapter
    }

    private fun setThemeGreen() {
        layout = R.layout.spinner_item_dark
    }

    private fun setThemeWhite() {
        layout = R.layout.spinner_item_light
    }
}