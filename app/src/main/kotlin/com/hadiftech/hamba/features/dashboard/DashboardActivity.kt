package com.hadiftech.hamba.features.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.core.HambaFrameActivity
import com.hadiftech.hamba.core.session.Session
import com.hadiftech.hamba.core.session.User
import com.hadiftech.hamba.core.views.HambaTextView
import com.hadiftech.hamba.features.login.LoginActivity
import com.hadiftech.hamba.features.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_main.*

class DashboardActivity : HambaBaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.abs_layout)
        supportActionBar!!.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.header_background))

        bottom_nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))
        navigationDrawerView.setupWithNavController(findNavController(R.id.nav_host_fragment))

        drawerLayout = findViewById(R.id.drawer_layout)
        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment), drawerLayout)
        navigationDrawerView.setNavigationItemSelectedListener(this)

        setNavigationDrawerHeaderData()
    }

    private fun setNavigationDrawerHeaderData() {
        val headerView = navigationDrawerView.getHeaderView(0)
        val textViewUserName = headerView.findViewById(R.id.textView_userName) as HambaTextView
        if (User.getUserName().isNotEmpty()) {
            textViewUserName.text = User.getUserName()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(drawerLayout) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        val bundle = Bundle()
        menuItem.isChecked = true
        drawerLayout.closeDrawers()

        when (menuItem.itemId) {
            R.id.item_manage_profile -> {
                bundle.putString(HambaFrameActivity.FRAGMENT_NAME_STRING, ProfileFragment::class.java.name)
                bundle.putString(HambaFrameActivity.ACTIVITY_TITLE, resources.getString(R.string.edit_profile))
                bundle.putBoolean(HambaFrameActivity.INFLATE_OPTIONS_MENU, true)
                startActivity(Intent(applicationContext, HambaFrameActivity::class.java).putExtras(bundle))
            }
            R.id.item_discovery_rules -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }
            R.id.item_account_settings -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }
            R.id.item_manage_book_address -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }
            R.id.item_manage_payments -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }
            R.id.item_my_orders -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }
            R.id.item_special_offers -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }
            R.id.item_hamba_gold -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }
            R.id.item_sign_out -> {
                Session.clearSession()
                val loginIntent = Intent(this, LoginActivity::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(loginIntent)
                finish()
            }
            R.id.item_support -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }
            R.id.item_faqs -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }
            R.id.item_rate_us -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}