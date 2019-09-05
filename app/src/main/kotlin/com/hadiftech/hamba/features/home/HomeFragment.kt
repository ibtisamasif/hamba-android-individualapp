package com.hadiftech.hamba.features.home

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseFragment

class HomeFragment : HambaBaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textViewCurrentLocation: TextView = root.findViewById(R.id.tvCurrentLocation)

        homeViewModel.text.observe(this, Observer {
            textViewCurrentLocation.text = it
        })

        bannerAnimation(root)
        val llTopDealsHorizontalScroll: LinearLayout = root.findViewById(R.id.llTopDealsHorizontalScroll)
        llTopDealsHorizontalScroll.animation = topDealsAnimation()

        return root
    }

    private fun bannerAnimation(root: View){
        val animation = AnimationDrawable()
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_1), 2000)
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_2), 2500)
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_3), 3000)
        animation.isOneShot = false

        val ivBanner: ImageView = root.findViewById(R.id.ivBanner)
        ivBanner.setBackgroundDrawable(animation)
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