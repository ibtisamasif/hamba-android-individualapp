package com.hadiftech.hamba.features.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity

class ProfileActivity: HambaBaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

    fun onSaveButtonClicked(bSave: View) {
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
    }
}