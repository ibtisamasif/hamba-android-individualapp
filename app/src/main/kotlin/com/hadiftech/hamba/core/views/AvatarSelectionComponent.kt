package com.hadiftech.hamba.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.listeners.OnAvatarClickListener
import kotlinx.android.synthetic.main.layout_avatar_selection.view.*

class AvatarSelectionComponent : LinearLayout {

    private val maleAvatarsArray: IntArray = intArrayOf(R.drawable.male_avatar_1, R.drawable.male_avatar_2, R.drawable.male_avatar_3, R.drawable.male_avatar_4,
        R.drawable.male_avatar_5, R.drawable.male_avatar_6, R.drawable.male_avatar_7, R.drawable.male_avatar_8, R.drawable.male_avatar_9)

    private val femaleAvatarsArray: IntArray = intArrayOf(R.drawable.female_avatar_1, R.drawable.female_avatar_2, R.drawable.female_avatar_3, R.drawable.female_avatar_4,
        R.drawable.female_avatar_5, R.drawable.female_avatar_6, R.drawable.female_avatar_7, R.drawable.female_avatar_8, R.drawable.female_avatar_9)

    lateinit var mView: View
    private var onAvatarClickListener: OnAvatarClickListener? = null

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
        mView = inflater.inflate(R.layout.layout_avatar_selection, this)

        avatar_radioButtonMale.setOnCheckedChangeListener { _, isChecked -> if(isChecked) { displayMaleAvatars() } }
        avatar_radioButtonFemale.setOnCheckedChangeListener { _, isChecked -> if(isChecked) { displayFemaleAvatars() } }
    }

    fun displayMaleAvatars(){
        linearLayout_avatarsList.removeAllViews()
        maleAvatarsArray.forEach {
            populateAvatarsList(it)
        }
    }

    fun displayFemaleAvatars(){
        linearLayout_avatarsList.removeAllViews()
        femaleAvatarsArray.forEach {
            populateAvatarsList(it)
        }
    }

    private fun populateAvatarsList(resId: Int) {

        var imageViewLayoutParams = LayoutParams(context.resources.getDimensionPixelSize(R.dimen._35sdp), context.resources.getDimensionPixelSize(R.dimen._35sdp))
        imageViewLayoutParams.setMargins(0, 0, 0, context.resources.getDimensionPixelOffset(R.dimen._25sdp))

        var imageView = ImageView(context)
        imageView.setImageResource(resId)
        imageView.layoutParams = imageViewLayoutParams

        if (onAvatarClickListener != null) {
            imageView.setOnClickListener { onAvatarClickListener!!.onAvatarClicked(resId) }
        }

        linearLayout_avatarsList.addView(imageView)
    }

    fun setOnAvatarClickListener(onAvatarClickListener: OnAvatarClickListener) {
        this.onAvatarClickListener = onAvatarClickListener
    }
}