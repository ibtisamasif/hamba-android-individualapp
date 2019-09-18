package com.hadiftech.hamba.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.AttrRes
import com.hadiftech.hamba.R
import kotlinx.android.synthetic.main.layout_gender_selection_view.view.*

class HambaGenderComponent : LinearLayout {

    lateinit var mView: View
    lateinit var radioGroup: RadioGroup

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
        mView = inflater.inflate(R.layout.layout_gender_selection_view, this)
        radioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)

        setRadioButtonSelectionListener()
    }

    private fun setRadioButtonSelectionListener() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = group.findViewById<View>(checkedId) as RadioButton
            if (checkedRadioButton.isChecked) {
                textView_gender.text = checkedRadioButton.tag as CharSequence?
            }
        }
    }

    fun getGender(): String {
        return textView_gender.text.toString()
    }

    fun setGender(gender: String) {
        if (gender.equals(radioButton_male.tag as CharSequence)) {
            radioButton_male.isChecked = true
            textView_gender.text = radioButton_male.tag as CharSequence?
        } else {
            radioButton_female.isChecked = true
            textView_gender.text = radioButton_female.tag as CharSequence?
        }
    }
}