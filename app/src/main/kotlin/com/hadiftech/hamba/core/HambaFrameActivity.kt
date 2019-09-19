package com.hadiftech.hamba.core

import android.os.Bundle
import android.view.MenuItem

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

import com.hadiftech.hamba.R

class HambaFrameActivity : HambaBaseActivity() {
    private var inflateOptionsMenu = true
    private var fragmentName: String? = null
    private var activityTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame_app_bar)
        val toolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val bundle = intent.extras
        if (bundle != null) {
            fragmentName = bundle.getString(FRAGMENT_NAME_STRING)
            inflateOptionsMenu = bundle.getBoolean(INFLATE_OPTIONS_MENU)
            activityTitle = bundle.getString(ACTIVITY_TITLE)
        }
        if (fragmentName != null) {
            val fragment = Fragment.instantiate(applicationContext, fragmentName!!)
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.mainFrameLayoutFrameActivity, fragment)
            transaction.commit()
        }
        supportActionBar!!.setTitle(activityTitle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val FRAGMENT_NAME_STRING = "fragment_name"
        const val FRAGMENT_BUNDLE_PASS = "fragment_bundle"
        const val INFLATE_OPTIONS_MENU = "inflate_options_menu"
        const val ACTIVITY_TITLE = "activity_title"
    }
}