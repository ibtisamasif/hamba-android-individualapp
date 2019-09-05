package com.hadiftech.hamba.features.home

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : HambaBaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.text.observe(this, Observer {
            tvCurrentLocation.text = it
        })

        bannerAnimation()
        llTopDealsHorizontalScroll.animation = topDealsAnimation()
    }

    private fun bannerAnimation(){
        val animation = AnimationDrawable()
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_1), 2000)
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_2), 2500)
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_3), 3000)
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