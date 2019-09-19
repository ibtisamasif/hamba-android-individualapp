package com.hadiftech.hamba.features.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
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
import com.hadiftech.hamba.features.order.ProfileFragment
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_main.*

class DashboardActivity : HambaBaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        bottom_nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))
        navigationDrawerView.setupWithNavController(findNavController(R.id.nav_host_fragment))

        drawerLayout = findViewById(R.id.drawer_layout)
        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment), drawerLayout)

        navigationDrawerView.setNavigationItemSelectedListener(this)
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

        menuItem.isChecked = true
        drawerLayout.closeDrawers()

        val intent: Intent
        val bundle = Bundle()

        when (menuItem.itemId) {
            R.id.item_manage_profile -> {
                bundle.putString(HambaFrameActivity.FRAGMENT_NAME_STRING, ProfileFragment::class.java.name)
                bundle.putString(HambaFrameActivity.ACTIVITY_TITLE, resources.getString(R.string.edit_profile))
                bundle.putBoolean(HambaFrameActivity.INFLATE_OPTIONS_MENU, true)
                intent = Intent(applicationContext, HambaFrameActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
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
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
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