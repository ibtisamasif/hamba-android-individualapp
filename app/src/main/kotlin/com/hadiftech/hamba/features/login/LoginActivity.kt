package com.hadiftech.hamba.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.features.signup.JoinUsActivity

class LoginActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onCreateAccountClicked(createAccountTextView: View){
        val joinUsIntent = Intent(this, JoinUsActivity::class.java)
        startActivity(joinUsIntent)
    }
}
