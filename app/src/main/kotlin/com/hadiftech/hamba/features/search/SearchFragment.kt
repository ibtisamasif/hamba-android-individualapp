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

class SearchFragment : HambaBaseFragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val textView: TextView = root.findViewById(R.id.text)
        searchViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}