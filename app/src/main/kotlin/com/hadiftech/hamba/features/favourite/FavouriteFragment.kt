package com.hadiftech.hamba.features.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseFragment

class FavouriteFragment : HambaBaseFragment() {

    private lateinit var favouriteViewModel: FavouriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        favouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favourite, container, false)
        val textView: TextView = root.findViewById(R.id.text)

        favouriteViewModel.text.observe(this, Observer {
            textView.text = it
        })

        return root
    }
}