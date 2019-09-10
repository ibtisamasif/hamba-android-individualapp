package com.hadiftech.hamba.features.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity: HambaBaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        populatePersonTypeDropDown()
        populatePrefixDropDown()
        populateNationalityDropDown()
        populateIdentityDropDown()
        populateCountryDropDown()
        populateAddressTypeDropDown()
        populateInterestDropDown()
    }

    private fun populatePersonTypeDropDown() {
        val items = ArrayList<String>()
        items.add("Service Provider")
        spPersonType.populate(this, R.layout.spinner_item_dark, items)
    }

    private fun populatePrefixDropDown() {
        val items = ArrayList<String>()
        items.add("Mr.")
        spPrefix.populate(this, R.layout.spinner_item_light, items)
    }

    private fun populateNationalityDropDown() {
        val items = ArrayList<String>()
        items.add("Select Nationality")
        spNationality.populate(this, R.layout.spinner_item_dark, items)
    }

    private fun populateIdentityDropDown() {
        val items = ArrayList<String>()
        items.add("Select Identity")
        spIdentity.populate(this, R.layout.spinner_item_dark, items)
    }

    private fun populateCountryDropDown() {
        val items = ArrayList<String>()
        items.add("Select Country")
        spCountry.populate(this, R.layout.spinner_item_dark, items)
    }

    private fun populateAddressTypeDropDown() {
        val items = ArrayList<String>()
        items.add("Select Address Type")
        spAddressType.populate(this, R.layout.spinner_item_dark, items)
    }

    private fun populateInterestDropDown() {
        val items = ArrayList<String>()
        items.add("Select Interest")
        spInterest.populate(this, R.layout.spinner_item_dark, items)
    }

    fun onSaveButtonClicked(bSave: View) {
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
    }
}