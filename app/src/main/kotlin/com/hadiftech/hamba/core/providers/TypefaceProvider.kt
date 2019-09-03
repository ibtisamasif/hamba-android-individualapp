package com.hadiftech.hamba.core.providers

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.Fonts

interface TypefaceProvider {

    fun getTypefaceFromXml(context: Context, attrs: AttributeSet?): Typeface? {

        var fontTypeface: Typeface? = null
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HambaFontAttributes, 0, 0)
        val fontType = typedArray.getInteger(R.styleable.HambaFontAttributes_fontType, 2)

        when(fontType){
            0 -> fontTypeface = Typeface.createFromAsset(context.assets, Fonts.CO_HEADLINE_W01_REGULAR)
            1 -> fontTypeface = Typeface.createFromAsset(context.assets, Fonts.CO_HEADLINE_W01_BOLD)
            2 -> fontTypeface = Typeface.createFromAsset(context.assets, Fonts.CO_HEADLINE_W01_LIGHT)
        }

        typedArray.recycle()
        return fontTypeface
    }
}