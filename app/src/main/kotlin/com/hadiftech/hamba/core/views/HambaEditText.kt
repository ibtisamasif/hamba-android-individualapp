package com.hadiftech.hamba.core.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.EditText
import androidx.annotation.AttrRes
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.Fonts

class HambaEditText : EditText {

    lateinit var fontTypeface: Typeface

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HambaFontAttributes, 0, 0)
        val fontType = typedArray.getInteger(R.styleable.HambaFontAttributes_fontType, 0)

        when(fontType){
            0 -> fontTypeface = Typeface.createFromAsset(context.assets, Fonts.DUCO_HEADLINE_REGULAR)
            1 -> fontTypeface = Typeface.createFromAsset(context.assets, Fonts.DUCO_HEADLINE_BOLD)
            2 -> fontTypeface = Typeface.createFromAsset(context.assets, Fonts.DUCO_HEADLINE_LIGHT)
        }

        typedArray.recycle()
        this.typeface = fontTypeface
    }
}