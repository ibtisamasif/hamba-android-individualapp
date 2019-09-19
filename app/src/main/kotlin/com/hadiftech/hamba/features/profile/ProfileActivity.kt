package com.hadiftech.hamba.features.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.core.services.HttpErrorCodes
import com.hadiftech.hamba.features.profile.edit_profile_service.EditUserIndividualProfileRequest
import com.hadiftech.hamba.features.profile.edit_profile_service.EditUserIndividualProfileResponse
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

        if (apiResponse is EditUserIndividualProfileResponse) {
            if (apiResponse.success!!) {
                Toast.makeText(this, "Record Edited Successfully", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialogProvider.showAlertDialog(this, apiResponse.message)
            }
        }
    }

    override fun onApiFailure(errorCode: Int) {
        if (errorCode == HttpErrorCodes.Unauthorized.code) {
            AlertDialogProvider.showAlertDialog(this, getString(R.string.password_incorrect))
        } else {
            super.onApiFailure(errorCode)
        }
    }

    private fun updateUI(details: GetProfileResponse.Details) {
        if (details.personType != null && details.personType!!.isNotEmpty()) {

        }

        if (details.nationality != null && details.nationality!!.isNotEmpty()) {

        }

        if (details.birthDate != null && details.birthDate!!.isNotEmpty()) {

        }

        if (details.prefix != null && details.prefix!!.isNotEmpty()) {

        }

        if (details.gender != null && details.gender!!.isNotEmpty()) {

        }

        if (details.number != null && details.number!!.isNotEmpty()) {

        }

        if (details.addressType != null && details.addressType!!.isNotEmpty()) {

        }

        if (details.address != null && details.address!!.isNotEmpty()) {

        }

        if (details.cityName != null && details.cityName!!.isNotEmpty()) {

        }

        if (details.country != null && details.country!!.isNotEmpty()) {

        }

        if (details.zipCode != null && details.zipCode!!.isNotEmpty()) {

        }

        if (details.email != null && details.email!!.isNotEmpty()) {

        }

        if (details.firstName != null && details.firstName!!.isNotEmpty()) {
            editText_firstName.setText(details.firstName)
        }

        if (details.middleName != null && details.middleName!!.isNotEmpty()) {

        }

        if (details.lastName != null && details.lastName!!.isNotEmpty()) {

        }

        if (details.avatar != null && details.avatar!!.isNotEmpty()) {

        }
    }

    private fun populatePersonTypeDropDown() {
        val items = ArrayList<String>()
        items.add("Service Provider")
        spinner_personType.populate(this, items)
    }

    private fun populatePrefixDropDown() {
        val items = ArrayList<String>()
        items.add("Mr.")
        items.add("Mrs.")
        items.add("Miss")
        spinner_prefix.populate(this, items)
    }

    private fun populateNationalityDropDown() {
        val items = ArrayList<String>()
        items.add("Select Nationality")
        spinner_nationality.populate(this, items)
    }

    private fun populateIdentityDropDown() {
        val items = ArrayList<String>()
        items.add("Select Identity")
        spinner_identity.populate(this, items)
    }

    private fun populateCountryDropDown() {
        val items = ArrayList<String>()
        items.add("Select Country")
        spinner_country.populate(this, items)
    }

    private fun populateAddressTypeDropDown() {
        val items = ArrayList<String>()
        items.add("Select Address Type")
        spinner_addressType.populate(this, items)
    }

    private fun populateInterestDropDown() {
        val items = ArrayList<String>()
        items.add("Select Interest")
        spinner_interest.populate(this, items)
    }

    fun onSaveButtonClicked(saveButton: View) {
        val editUserIndividualProfileRequest = EditUserIndividualProfileRequest()
        editUserIndividualProfileRequest.firstName = editText_firstName.getText()
        editUserIndividualProfileRequest.middleName = editText_middleName.getText()
        editUserIndividualProfileRequest.lastName = editText_lastName.getText()
        editUserIndividualProfileRequest.gender = editText_gender.getText()
        editUserIndividualProfileRequest.birthDate = editText_dateOfBirth.getText()
        editUserIndividualProfileRequest.prefix = "test"
        editUserIndividualProfileRequest.nationality = "test"
        editUserIndividualProfileRequest.registrationNo = "test"
        editUserIndividualProfileRequest.registrationType = "test"

        if (checkValidations()) {
            APiManager.editUserIndividualProfileApi(this, this, editUserIndividualProfileRequest)
        }
    }

    private fun checkValidations(): Boolean {
        // added sample validations just to complete structure

        if (editText_firstName.getText().length < 4) {
            editText_firstName.setError("Please enter valid first name")
            return false
        }
        if (editText_lastName.getText().isEmpty()) {
            editText_lastName.setError("Please enter valid last name")
            return false
        }
        if (editText_gender.getText().isEmpty()) {
            editText_gender.setError("Please enter valid gender")
            return false
        }
        if (editText_dateOfBirth.getText().isEmpty()) {
            editText_dateOfBirth.setError("Please enter valid date of birth")
            return false
        }

        // prefix

        //nationality

        //registrationNo

        //registrationType

        return true
    }
}