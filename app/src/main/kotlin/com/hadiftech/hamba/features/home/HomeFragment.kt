package com.hadiftech.hamba.features.home

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hadiftech.hamba.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.tvCurrentLocation)

        val animation = AnimationDrawable()
        animation.addFrame(resources.getDrawable(R.drawable.image_1), 2000)
        animation.addFrame(resources.getDrawable(R.drawable.image_2), 2500)
        animation.addFrame(resources.getDrawable(R.drawable.image_3), 3000)
        animation.isOneShot = false
        val tvBanner: ImageView = root.findViewById(R.id.tvBanner)
        tvBanner.setBackgroundDrawable(animation)
        // start the animation!
        animation.start()

        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}