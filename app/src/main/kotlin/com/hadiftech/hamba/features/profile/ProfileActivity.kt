package com.hadiftech.hamba.features.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.HambaBaseActivity
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.core.services.HttpErrorCodes
import com.hadiftech.hamba.features.profile.edit_profile_service.EditUserIndividualProfileRequest
import com.hadiftech.hamba.features.profile.edit_profile_service.EditUserIndividualProfileResponse
import com.hadiftech.hamba.features.profile.get_profile_service.GetProfileResponse
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import kotlinx.android.synthetic.main.activity_profile.*
import java.text.SimpleDateFormat
import java.util.*


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
        setDatePickListener()

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
                Toast.makeText(this, getString(R.string.record_edited_successfully), Toast.LENGTH_SHORT).show()
            } else {
                AlertDialogProvider.showAlertDialog(this, apiResponse.message)
            }
        }
    }

    override fun onApiFailure(errorCode: Int) {
        when (errorCode) {
            HttpErrorCodes.Unauthorized.code -> AlertDialogProvider.showAlertDialog(this, getString(R.string.action_unauthorized))
            HttpErrorCodes.Forbidden.code -> AlertDialogProvider.showAlertDialog(this, getString(R.string.action_forbidden))
            HttpErrorCodes.NotFound.code -> AlertDialogProvider.showAlertDialog(this, getString(R.string.not_found))
            else -> super.onApiFailure(errorCode)
        }
    }

    private fun updateUI(details: GetProfileResponse.Details) {

        swPushNotifications.isChecked = details.enableNotification!!

        if (details.personType != null && details.personType!!.isNotEmpty()) {
            spinner_personType.setSelection(resources.getStringArray(R.array.person_types).indexOf(details.personType))
        }

        if (details.nationality != null && details.nationality!!.isNotEmpty()) {
            spinner_nationality.setSelection(resources.getStringArray(R.array.nationalities).indexOf(details.nationality))
        }

        if (details.birthDate != null && details.birthDate!!.isNotEmpty()) {

        }

        if (details.prefix != null && details.prefix!!.isNotEmpty()) {
            spinner_prefix.setSelection(resources.getStringArray(R.array.prefix_list).indexOf(details.prefix))
        }

        if (details.gender != null && details.gender!!.isNotEmpty()) {
            editText_gender.setGender(details.gender!!)
        }

        if (details.number != null && details.number!!.isNotEmpty()) {
            var phoneNumber = PhoneNumberUtil.createInstance(this).parse(details.number, Constants.EMPTY_STRING)
            editText_phone.setPhoneNumber(phoneNumber.nationalNumber.toString())
            editText_phone.setCountryCode(phoneNumber.countryCode)
        }

        if (details.addressType != null && details.addressType!!.isNotEmpty()) {
            spinner_addressType.setSelection(resources.getStringArray(R.array.address_type).indexOf(details.addressType))
        }

        if (details.address != null && details.address!!.isNotEmpty()) {
            editText_address.setText(details.address!!)
        }

        if (details.cityName != null && details.cityName!!.isNotEmpty()) {
            editText_city.setText(details.cityName!!)
        }

        if (details.country != null && details.country!!.isNotEmpty()) {
            spinner_country.setSelection(resources.getStringArray(R.array.countries).indexOf(details.country))
        }

        if (details.zipCode != null && details.zipCode!!.isNotEmpty()) {
            editText_zipCode.setText(details.zipCode!!)
        }

        if (details.email != null && details.email!!.isNotEmpty()) {
            editText_email.setText(details.email!!)
        }

        if (details.firstName != null && details.firstName!!.isNotEmpty()) {
            editText_firstName.setText(details.firstName!!)
        }

        if (details.middleName != null && details.middleName!!.isNotEmpty()) {
            editText_middleName.setText(details.middleName!!)
        }

        if (details.lastName != null && details.lastName!!.isNotEmpty()) {
            editText_lastName.setText(details.lastName!!)
        }

        if (details.avatar != null && details.avatar!!.isNotEmpty()) {

        }

        if (!details.interests.isNullOrEmpty()) {

        }
    }

    private fun setDatePickListener() {
        editText_dateOfBirth.setEditTextClickable(false)
        editText_dateOfBirth.setOnClickListener {
            displayDatePicker()
        }
    }

    private fun displayDatePicker() {
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            editText_dateOfBirth.setText(SimpleDateFormat("MMM/dd/yyyy").format(calendar.time))
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun populatePersonTypeDropDown() {
        spinner_personType.populate(this, R.array.person_types)
    }

    private fun populatePrefixDropDown() {
        spinner_prefix.populate(this, R.array.prefix_list)
    }

    private fun populateNationalityDropDown() {
        spinner_nationality.populate(this, R.array.nationalities)
    }

    private fun populateIdentityDropDown() {
        spinner_identity.populate(this, R.array.identities)
    }

    private fun populateCountryDropDown() {
        spinner_country.populate(this, R.array.countries)
    }

    private fun populateAddressTypeDropDown() {
        spinner_addressType.populate(this, R.array.address_type)
    }

    private fun populateInterestDropDown() {
        spinner_interest.populate(this, R.array.interests)
    }

    fun onSaveButtonClicked(saveButton: View) {
        //Required fields
        val editUserIndividualProfileRequest = EditUserIndividualProfileRequest()
        editUserIndividualProfileRequest.personType = spinner_personType.selectedItem.toString()
        editUserIndividualProfileRequest.prefix = spinner_prefix.selectedItem.toString()
        editUserIndividualProfileRequest.firstName = editText_firstName.getText()
        editUserIndividualProfileRequest.lastName = editText_lastName.getText()
        editUserIndividualProfileRequest.gender = editText_gender.getText()
        editUserIndividualProfileRequest.birthDate = editText_dateOfBirth.getText()
        editUserIndividualProfileRequest.nationality = spinner_nationality.selectedItem.toString()
        //ToDo refactor code below
        editUserIndividualProfileRequest.registrationNo = "9999"
        editUserIndividualProfileRequest.registrationType = "text"
        editUserIndividualProfileRequest.imgExtension = "text"
        val selectedInterests = ArrayList<String>()
        selectedInterests.add(spinner_interest.selectedItem.toString())
        editUserIndividualProfileRequest.interests = selectedInterests

        //Non-Required fields
        editUserIndividualProfileRequest.middleName = editText_middleName.getText()
        editUserIndividualProfileRequest.cityName = editText_city.getText()
        editUserIndividualProfileRequest.cityName = editText_zipCode.getText()
        editUserIndividualProfileRequest.country = spinner_country.selectedItem.toString()
        editUserIndividualProfileRequest.email = editText_email.getText()
        editUserIndividualProfileRequest.number = editText_phone.getPhoneNumber()
        editUserIndividualProfileRequest.addressType = spinner_addressType.selectedItem.toString()
        editUserIndividualProfileRequest.address = editText_address.getText()

        if (checkValidations()) {
            APiManager.editUserIndividualProfileApi(this, this, editUserIndividualProfileRequest)
        }
    }

    private fun checkValidations(): Boolean {
        // added sample validations just to complete structure

        if (editText_firstName.getText().isEmpty() || editText_firstName.getText().length < 3) {
            editText_firstName.setError(getString(R.string.please_enter_valid_first_name))
            return false
        }
        if (editText_lastName.getText().isEmpty() || editText_firstName.getText().length < 3) {
            editText_lastName.setError(getString(R.string.please_enter_valid_last_name))
            return false
        }
        if (editText_gender.getText().isEmpty() || editText_firstName.getText().length < 3) {
            editText_gender.setError(getString(R.string.please_enter_valid_gender))
            return false
        }
        if (editText_dateOfBirth.getText().isEmpty() || editText_firstName.getText().length < 3) {
            editText_dateOfBirth.setError(getString(R.string.please_enter_valid_date_of_birth))
            return false
        }

        // prefix

        //nationality

        //registrationNo

        //registrationType

        return true
    }
}