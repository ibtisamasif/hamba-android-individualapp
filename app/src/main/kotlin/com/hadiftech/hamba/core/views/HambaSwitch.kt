package com.hadiftech.hamba.core.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Switch
import androidx.annotation.AttrRes
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.providers.TypefaceProvider

class HambaSwitch : Switch , TypefaceProvider {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {

        this.typeface = getTypefaceFromXml(context, attrs)
        setThumbResource(R.drawable.switch_thumb_inactive)

        setOnCheckedChangeListener { _, isChecked ->
            setDisplay(isChecked)
        }
    }

    private fun setDisplay(isChecked: Boolean) {
        if (isChecked) {
            setThumbResource(R.drawable.switch_thumb_active)
        } else {
            setThumbResource(R.drawable.switch_thumb_inactive)
        }
    }
}