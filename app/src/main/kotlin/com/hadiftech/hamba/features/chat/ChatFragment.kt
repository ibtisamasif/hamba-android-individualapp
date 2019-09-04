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

class ChatFragment : HambaBaseFragment() {

    private lateinit var chatViewModel: ChatViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        chatViewModel =
            ViewModelProviders.of(this).get(ChatViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chat, container, false)
        val textView: TextView = root.findViewById(R.id.text)
        chatViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}