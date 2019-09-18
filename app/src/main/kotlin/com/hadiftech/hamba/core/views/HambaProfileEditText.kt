package com.hadiftech.hamba.core.views

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.providers.TypefaceProvider
import kotlinx.android.synthetic.main.layout_hamba_login_edittext.view.*
import android.text.InputFilter
import android.text.TextWatcher

class HambaProfileEditText : RelativeLayout, TypefaceProvider {

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

        val rightDrawableVisibility = typedArray.getBoolean(R.styleable.HambaLoginEditTextAttributes_showRightDrawable, false)
        val counterVisibility = typedArray.getBoolean(R.styleable.HambaLoginEditTextAttributes_showCounter, false)
        showRightViewContainer(rightDrawableVisibility, counterVisibility)
        showRightDrawable(rightDrawableVisibility)
        showCounter(counterVisibility)

        imageView_rightDrawable.setImageResource(typedArray.getResourceId(R.styleable.HambaLoginEditTextAttributes_android_drawableRight, R.drawable.help_icon))
        editText_loginField.inputType = typedArray.getInt(R.styleable.HambaLoginEditTextAttributes_android_inputType, InputType.TYPE_CLASS_TEXT)
        editText_loginField.gravity = typedArray.getInt(R.styleable.HambaLoginEditTextAttributes_android_gravity, Gravity.CENTER_VERTICAL)
        editText_loginField.hint = typedArray.getString(R.styleable.HambaLoginEditTextAttributes_android_hint)
        editText_loginField.typeface = getTypefaceFromXml(context, attrs)

        val maxLength = typedArray.getInt(R.styleable.HambaLoginEditTextAttributes_android_maxLength, 40)
        editText_loginField.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))

        val themeType = typedArray.getInteger(R.styleable.HambaLoginEditTextAttributes_editTextTheme, 0)
        when (themeType) {
            0 -> setThemeWhite(context)
            1 -> setThemeGreen(context)
        }

        typedArray.recycle()
    }

    fun showPasswordVisibilityToggle(visibility: Boolean) {
        inputLayout_loginField.isPasswordVisibilityToggleEnabled = visibility
    }

    fun setError(error: String) {
        editText_loginField.error = error
    }

    fun getText() : String {
        return editText_loginField.text.toString()
    }

    fun setText(text: String) {
        editText_loginField.setText(text)
    }

    fun setHint(hint: String) {
        editText_loginField.hint = hint
    }

    fun setInputType(inputType: Int) {
        editText_loginField.inputType = inputType
    }

    fun setRightDrawable(resourceId: Int) {
        imageView_rightDrawable.setImageResource(resourceId)
    }

    fun setEditTextClickable(value: Boolean) {
        editText_loginField.isEnabled = value
    }

    private fun showRightViewContainer(drawableVisibility: Boolean, counterVisibility: Boolean) {
        if (drawableVisibility || counterVisibility) {
            relativeLayout_rightViewContainer.visibility = View.VISIBLE
        } else {
            relativeLayout_rightViewContainer.visibility = View.GONE
        }
    }

    private fun showRightDrawable(visibility: Boolean) {
        if (visibility) {
            imageView_rightDrawable.visibility = View.VISIBLE
        } else {
            imageView_rightDrawable.visibility = View.GONE
        }
    }

    private fun showCounter(visibility: Boolean) {
        if (visibility) {
            textView_Counter.text = context.resources.getString(R.string.counter_text, 0)
            textView_Counter.visibility = View.VISIBLE
            setTextChangedListener()
        } else {
            textView_Counter.visibility = View.GONE
        }
    }

    private fun setThemeGreen(context: Context) {
        textView_Counter.setTextColor(ContextCompat.getColor(context, R.color.colorGreenLight))
        editText_loginField.setTextColor(ContextCompat.getColor(context, R.color.colorGreenLight))
        editText_loginField.setHintTextColor(ContextCompat.getColor(context, R.color.colorGreenLight))
        loginField_Container.background = ContextCompat.getDrawable(context, R.drawable.login_fields_green_bg)
        inputLayout_loginField.setPasswordVisibilityToggleTintList(ContextCompat.getColorStateList(context, R.color.colorGreenLight))
    }

    private fun setThemeWhite(context: Context) {
        textView_Counter.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        editText_loginField.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        editText_loginField.setHintTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        loginField_Container.background = ContextCompat.getDrawable(context, R.drawable.login_fields_white_bg)
        inputLayout_loginField.setPasswordVisibilityToggleTintList(ContextCompat.getColorStateList(context, R.color.colorWhite))
    }

    fun setTextChangedListener(textChangeListener: TextWatcher) {
        editText_loginField.addTextChangedListener(textChangeListener)
    }

    private fun setTextChangedListener() {
        setTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textView_Counter.text = context.resources.getString(R.string.counter_text, s.length)
            }
        })
    }
}