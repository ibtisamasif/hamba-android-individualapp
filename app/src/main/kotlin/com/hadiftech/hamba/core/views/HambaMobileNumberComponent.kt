package com.hadiftech.hamba.core.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.Fonts
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import kotlinx.android.synthetic.main.layout_mobile_number_view.view.*

class HambaMobileNumberComponent : LinearLayout {

    private lateinit var mView: View
    private lateinit var mPhoneNumberEditText: HambaEditText
    private lateinit var mCountryCodePicker: CountryCodePicker

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mView = inflater.inflate(R.layout.layout_mobile_number_view, this)

        mCountryCodePicker = mView.findViewById(R.id.countryCodePicker)
        mPhoneNumberEditText = mView.findViewById(R.id.editText_phoneNumber)

        mCountryCodePicker.setTypeFace(Typeface.createFromAsset(context.assets, Fonts.CO_HEADLINE_W01_LIGHT));

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HambaLoginEditTextAttributes, 0, 0)
        val themeType = typedArray.getInteger(R.styleable.HambaLoginEditTextAttributes_editTextTheme, 0)
        when (themeType) {
            0 -> setThemeWhite(context)
            1 -> setThemeGreen(context)
        }
    }

    private fun setThemeGreen(context: Context) {
        mCountryCodePicker.textColor = ContextCompat.getColor(context, R.color.colorGreenLight)
        mCountryCodePicker.setSeparatorColor(ContextCompat.getColor(context, R.color.colorGreenLight))
        mPhoneNumberEditText.setTextColor(ContextCompat.getColor(context, R.color.colorGreenLight))
        mPhoneNumberEditText.setHintTextColor(ContextCompat.getColor(context, R.color.colorGreenLight))
        container_mobileNumberComponent.background = ContextCompat.getDrawable(context, R.drawable.login_fields_green_bg)
    }

    private fun setThemeWhite(context: Context) {
        mCountryCodePicker.textColor = (ContextCompat.getColor(context, R.color.colorWhite))
        mCountryCodePicker.setSeparatorColor(ContextCompat.getColor(context, R.color.colorWhite))
        mPhoneNumberEditText.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        mPhoneNumberEditText.setHintTextColor(ContextCompat.getColor(context, R.color.colorWhite))
        container_mobileNumberComponent.background = ContextCompat.getDrawable(context, R.drawable.login_fields_white_bg)
    }

    fun setError(error: String) {
        mPhoneNumberEditText.error = error
    }

    fun getPhoneNumberWithPrefix() : String {
        return mCountryCodePicker.selectedCountryCodeWithPlus + mPhoneNumberEditText.text
    }

    fun getPhoneNumber() : String {
        return mCountryCodePicker.selectedCountryCode + mPhoneNumberEditText.text
    }

    fun setPhoneNumber(number: String) {
        mPhoneNumberEditText.setText(number)
    }

    fun setCountryCode(code: Int) {
        mCountryCodePicker.setCountryForPhoneCode(code)
    }
}