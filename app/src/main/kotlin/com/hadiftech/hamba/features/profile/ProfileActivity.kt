package com.hadiftech.hamba.features.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.features.profile.get_profile_service.GetProfileResponse
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : HambaBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.edit_profile)

        populatePersonTypeDropDown()
        populatePrefixDropDown()
        populateNationalityDropDown()
        populateIdentityDropDown()
        populateCountryDropDown()
        populateAddressTypeDropDown()
        populateInterestDropDown()

        APiManager.getUserProfile(this, this)
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is GetProfileResponse) {
            if (apiResponse.details != null) {
                updateUI(apiResponse.details!!)
            } else {
                AlertDialogProvider.showAlertDialog(this, apiResponse.message)
            }
        }
    }

    private fun updateUI(details: GetProfileResponse.Details) {
        if(details.personType != null && details.personType!!.isNotEmpty()) {

        }

        if(details.nationality != null && details.nationality!!.isNotEmpty()) {

        }

        if(details.birthDate != null && details.birthDate!!.isNotEmpty()) {

        }

        if(details.prefix != null && details.prefix!!.isNotEmpty()) {

        }

        if(details.gender != null && details.gender!!.isNotEmpty()) {

        }

        if(details.number != null && details.number!!.isNotEmpty()) {

        }

        if(details.addressType != null && details.addressType!!.isNotEmpty()) {

        }

        if(details.address != null && details.address!!.isNotEmpty()) {

        }

        if(details.cityName != null && details.cityName!!.isNotEmpty()) {

        }

        if(details.country != null && details.country!!.isNotEmpty()) {

        }

        if(details.zipCode != null && details.zipCode!!.isNotEmpty()) {

        }

        if(details.email != null && details.email!!.isNotEmpty()) {

        }

        if(details.firstName != null && details.firstName!!.isNotEmpty()) {

        }

        if(details.middleName != null && details.middleName!!.isNotEmpty()) {

        }

        if(details.lastName != null && details.lastName!!.isNotEmpty()) {

        }

        if(details.avatar != null && details.avatar!!.isNotEmpty()) {

        }
    }

    private fun populatePersonTypeDropDown() {
        val items = ArrayList<String>()
        items.add("Service Provider")
        spinner_personType.populate(this, R.layout.spinner_item_dark, items)
    }

    private fun populatePrefixDropDown() {
        val items = ArrayList<String>()
        items.add("Mr.")
        items.add("Mrs.")
        items.add("Miss")
        spinner_prefix.populate(this, R.layout.spinner_item_light, items)
    }

    private fun populateNationalityDropDown() {
        val items = ArrayList<String>()
        items.add("Select Nationality")
        spinnerNationality.populate(this, R.layout.spinner_item_dark, items)
    }

    private fun populateIdentityDropDown() {
        val items = ArrayList<String>()
        items.add("Select Identity")
        spinner_identity.populate(this, R.layout.spinner_item_dark, items)
    }

    private fun populateCountryDropDown() {
        val items = ArrayList<String>()
        items.add("Select Country")
        spinner_country.populate(this, R.layout.spinner_item_dark, items)
    }

    private fun populateAddressTypeDropDown() {
        val items = ArrayList<String>()
        items.add("Select Address Type")
        spinner_addressType.populate(this, R.layout.spinner_item_dark, items)
    }

    private fun populateInterestDropDown() {
        val items = ArrayList<String>()
        items.add("Select Interest")
        spinner_interest.populate(this, R.layout.spinner_item_dark, items)
    }

    fun onSaveButtonClicked(bSave: View) {
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
    }
}