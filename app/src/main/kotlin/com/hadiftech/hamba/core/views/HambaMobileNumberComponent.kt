package com.hadiftech.hamba.core.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.Fonts
import com.rilixtech.CountryCodePicker

class HambaMobileNumberComponent : LinearLayout {

    lateinit var mView: View
    lateinit var mPhoneNumberEditText: HambaEditText
    lateinit var mCountryCodePicker: CountryCodePicker

    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context)
    }

    private fun initialize(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mView = inflater.inflate(R.layout.layout_account_number_view, this)

        mCountryCodePicker = mView.findViewById(R.id.countryCodePicker)
        mPhoneNumberEditText = mView.findViewById(R.id.editText_phoneNumber)

        mCountryCodePicker.setTypeFace(Typeface.createFromAsset(context.assets, Fonts.CO_HEADLINE_W01_LIGHT));
    }

    fun getPhoneNumberWithPrefix() : String {
        return mCountryCodePicker.selectedCountryCodeWithPlus + mPhoneNumberEditText.text
    }

    fun getPhoneNumber() : String {
        return mCountryCodePicker.selectedCountryCode + mPhoneNumberEditText.text
    }
}