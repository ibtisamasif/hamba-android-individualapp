package com.hadiftech.hamba.features.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity

class JoinUsActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_us)
    }

    fun onIndividualSignupClicked(individualSignupView: View){
        val individualSignupIntent = Intent(this, IndividualSignupActivity::class.java)
        startActivity(individualSignupIntent)
    }

    fun onBusinessSignupClicked(businessSignupView: View){
        // TODO: Move to Business Signup Page
    }
}
