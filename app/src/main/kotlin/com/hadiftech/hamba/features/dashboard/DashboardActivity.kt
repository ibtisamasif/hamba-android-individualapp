package com.hadiftech.hamba.features.dashboard

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_main.*

class DashboardActivity : HambaBaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
//    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

//        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))

        navigationView.setupWithNavController(findNavController(R.id.nav_host_fragment))

        drawerLayout = findViewById(R.id.drawer_layout)

        NavigationUI.setupActionBarWithNavController(
            this,
            findNavController(R.id.nav_host_fragment),
            drawerLayout
        )

        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            drawerLayout,
            Navigation.findNavController(this, R.id.nav_host_fragment)
        )
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

        val id = menuItem.itemId

        when (id) {

            R.id.item_manage_profile -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
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


            R.id.item_your_orders -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }


            R.id.item_special_offers -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }


            R.id.item_create_edit_store -> {
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

            R.id.item_rate_us -> {
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
            }
        }
        return true

    }
}