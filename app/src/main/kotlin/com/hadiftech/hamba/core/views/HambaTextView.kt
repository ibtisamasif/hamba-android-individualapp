package com.hadiftech.hamba.core.views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.AttrRes
import com.hadiftech.hamba.core.providers.TypefaceProvider
import android.graphics.Paint
import android.graphics.Rect


class HambaTextView : TextView , TypefaceProvider {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        this.typeface = getTypefaceFromXml(context, attrs)
    }
}