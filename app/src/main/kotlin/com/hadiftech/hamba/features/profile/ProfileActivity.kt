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
import java.util.*
import java.text.SimpleDateFormat

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

        swPushNotifications.isChecked = details.enableNotification!!

        if(details.personType != null && details.personType!!.isNotEmpty()) {
            spinner_personType.setSelection(resources.getStringArray(R.array.person_types).indexOf(details.personType))
        }

        if(details.nationality != null && details.nationality!!.isNotEmpty()) {
            spinner_nationality.setSelection(resources.getStringArray(R.array.nationalities).indexOf(details.nationality))
        }

        if(details.birthDate != null && details.birthDate!!.isNotEmpty()) {

        }

        if(details.prefix != null && details.prefix!!.isNotEmpty()) {
            spinner_prefix.setSelection(resources.getStringArray(R.array.prefix_list).indexOf(details.prefix))
        }

        if(details.gender != null && details.gender!!.isNotEmpty()) {
            editText_gender.setGender(details.gender!!)
        }

        if(details.number != null && details.number!!.isNotEmpty()) {
            var phoneNumber = PhoneNumberUtil.createInstance(this).parse(details.number, Constants.EMPTY_STRING)
            editText_phone.setPhoneNumber(phoneNumber.nationalNumber.toString())
            editText_phone.setCountryCode(phoneNumber.countryCode)
        }

        if(details.addressType != null && details.addressType!!.isNotEmpty()) {
            spinner_addressType.setSelection(resources.getStringArray(R.array.address_type).indexOf(details.addressType))
        }

        if(details.address != null && details.address!!.isNotEmpty()) {
            editText_address.setText(details.address!!)
        }

        if(details.cityName != null && details.cityName!!.isNotEmpty()) {
            editTextCity.setText(details.cityName!!)
        }

        if(details.country != null && details.country!!.isNotEmpty()) {
            spinner_country.setSelection(resources.getStringArray(R.array.countries).indexOf(details.country))
        }

        if(details.zipCode != null && details.zipCode!!.isNotEmpty()) {
            editText_zipCode.setText(details.zipCode!!)
        }

        if(details.email != null && details.email!!.isNotEmpty()) {
            editText_email.setText(details.email!!)
        }

        if(details.firstName != null && details.firstName!!.isNotEmpty()) {
            editText_firstName.setText(details.firstName!!)
        }

        if(details.middleName != null && details.middleName!!.isNotEmpty()) {
            editText_middleName.setText(details.middleName!!)
        }

        if(details.lastName != null && details.lastName!!.isNotEmpty()) {
            editText_lastName.setText(details.lastName!!)
        }

        if(details.avatar != null && details.avatar!!.isNotEmpty()) {

        }

        if(!details.interests.isNullOrEmpty()) {

        }
    }

    private fun setDatePickListener() {
        editText_dateOfBirth.setEditTextClickable(false)
        editText_dateOfBirth.setOnClickListener { _ ->
            displayDatePicker()
        }
    }

    private fun displayDatePicker() {
        DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, monthOfYear, dayOfMonth)
                editText_dateOfBirth.setText(SimpleDateFormat("MMM/dd/yyyy").format(calendar.time))
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ).show()
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