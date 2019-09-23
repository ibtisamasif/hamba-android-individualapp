package com.hadiftech.hamba.core.views

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.providers.TypefaceProvider
import kotlinx.android.synthetic.main.layout_hamba_profile_edittext.view.*

class HambaProfileEditText : RelativeLayout, TypefaceProvider {

    private var isPasswordToggleVisible: Boolean = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {

        inflate(context, R.layout.layout_hamba_profile_edittext, this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HambaLoginEditTextAttributes, 0, 0)

        val counterVisibility = typedArray.getBoolean(R.styleable.HambaLoginEditTextAttributes_showCounter, false)
        showCounter(counterVisibility)

        val rightImageResource = typedArray.getResourceId(R.styleable.HambaLoginEditTextAttributes_android_drawableRight, 0)
        setRightDrawable(rightImageResource)

        isPasswordToggleVisible = typedArray.getBoolean(R.styleable.HambaLoginEditTextAttributes_showPasswordToggle, false)
        showPasswordVisibilityToggle(isPasswordToggleVisible)

        editText_field.inputType = typedArray.getInt(R.styleable.HambaLoginEditTextAttributes_android_inputType, InputType.TYPE_CLASS_TEXT)
        editText_field.gravity = typedArray.getInt(R.styleable.HambaLoginEditTextAttributes_android_gravity, Gravity.CENTER_VERTICAL)
        editText_field.hint = typedArray.getString(R.styleable.HambaLoginEditTextAttributes_android_hint)
        editText_field.typeface = getTypefaceFromXml(context, attrs)

        val maxLength = typedArray.getInt(R.styleable.HambaLoginEditTextAttributes_android_maxLength, 40)
        editText_field.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))

        val themeType = typedArray.getInteger(R.styleable.HambaLoginEditTextAttributes_editTextTheme, 0)
        when (themeType) {
            0 -> setThemeWhite(context)
            1 -> setThemeGreen(context)
        }

        setTextChangedListener()
        typedArray.recycle()
    }

    fun showPasswordVisibilityToggle(visibility: Boolean) {
        val dimension = context.resources.getDimensionPixelSize(R.dimen._10sdp)
        if (visibility) {
            loginField_Container.setPadding(dimension,0,0,0)
        } else {
            loginField_Container.setPadding(dimension,0, dimension,0)
        }

        inputLayout_loginField.isPasswordVisibilityToggleEnabled = visibility
    }

    fun setError(error: String) {
        if (isPasswordToggleVisible) {
            showPasswordVisibilityToggle(false)
        }

        editText_field.error = error
    }

    fun getText(): String {
        return editText_field.text.toString()
    }

    fun setText(text: String) {
        editText_field.setText(text)
    }

    fun setHint(hint: String) {
        editText_field.hint = hint
    }

    fun setInputType(inputType: Int) {
        editText_field.inputType = inputType
    }

    fun setEditTextClickable(value: Boolean) {
        editText_field.isEnabled = value
    }

    private fun setRightDrawable(resId: Int) {
        if (resId != 0) {
            imageView_rightDrawable.visibility = View.VISIBLE
            relativeLayout_rightViewContainer.visibility = View.VISIBLE
            imageView_rightDrawable.setImageResource(resId)
        }
    }

    private fun showCounter(visibility: Boolean) {
        if (visibility) {
            textView_Counter.visibility = View.VISIBLE
            relativeLayout_rightViewContainer.visibility = View.VISIBLE
            textView_Counter.text = context.resources.getString(R.string.counter_text, 0)
        }
    }

    private fun setThemeGreen(context: Context) {
        textView_Counter.setTextColor(ContextCompat.getColor(context, R.color.colorGreenLight))
        editText_field.setTextColor(ContextCompat.getColor(context, R.color.colorGreenLight))
        editText_field.setHintTextColor(ContextCompat.getColor(context, R.color.colorGreenLight))
        loginField_Container.background = ContextCompat.getDrawable(context, R.drawable.login_fields_green_bg)
        inputLayout_loginField.setPasswordVisibilityToggleTintList(ContextCompat.getColorStateList(context, R.color.colorGreenLight))
    }

    private fun setThemeWhite(context: Context) {
        textView_Counter.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        editText_field.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        editText_field.setHintTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        loginField_Container.background = ContextCompat.getDrawable(context, R.drawable.login_fields_white_bg)
        inputLayout_loginField.setPasswordVisibilityToggleTintList(ContextCompat.getColorStateList(context, R.color.colorWhite))
    }

    fun setTextChangedListener(textChangeListener: TextWatcher) {
        editText_field.addTextChangedListener(textChangeListener)
    }

    private fun setTextChangedListener() {
        setTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textView_Counter.text = context.resources.getString(R.string.counter_text, s.length)

                if (isPasswordToggleVisible) {
                    showPasswordVisibilityToggle(true)
                }
            }
        })
    }
}