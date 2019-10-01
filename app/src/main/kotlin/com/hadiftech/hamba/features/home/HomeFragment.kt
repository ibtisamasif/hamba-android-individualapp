package com.hadiftech.hamba.features.home

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import androidx.core.content.ContextCompat
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseFragment
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.core.session.Session
import com.hadiftech.hamba.core.session.User
import com.hadiftech.hamba.features.profile.get_profile_service.GetProfileResponse
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : HambaBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bannerAnimation()
        llTopDealsHorizontalScroll.animation = topDealsAnimation()
    }

    override fun onResume() {
        super.onResume()

        if (Session.isSessionAvailable() && User.isCurrentProfileOutdated()) {
            APiManager.getUserProfile(activity!!, this)
        } else {
            textView_profilePercentage.text = String.format(getString(R.string._30_complete), User.getProfileUpdatePercentage())
        }
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is GetProfileResponse) {
            if (apiResponse.details != null) {
                User.saveUserProfile(apiResponse.details!!)
                textView_profilePercentage.text = String.format(getString(R.string._30_complete), User.getProfileUpdatePercentage())
            }
        }
    }

    private fun bannerAnimation(){
        val animation = AnimationDrawable()
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_1)!!, 2000)
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_2)!!, 2500)
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_3)!!, 3000)
        animation.isOneShot = false

        imageView_banner.setBackgroundDrawable(animation)
        animation.start()
    }

    private fun topDealsAnimation() : Animation{

        val translateAnimation: Animation = TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE,
            1000f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f)

        translateAnimation.duration = 10000
        translateAnimation.repeatCount = -1
        translateAnimation.repeatMode = Animation.REVERSE
        translateAnimation.interpolator = LinearInterpolator()

        return translateAnimation
    }
}