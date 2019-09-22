package com.hadiftech.hamba.features.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.HambaBaseFragment
import com.hadiftech.hamba.core.HambaUtils
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.core.services.HttpErrorCodes
import com.hadiftech.hamba.features.profile.edit_profile_service.IndividualProfileEditRequest
import com.hadiftech.hamba.features.profile.edit_profile_service.IndividualProfileEditResponse
import com.hadiftech.hamba.features.profile.get_profile_service.GetProfileResponse
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import kotlinx.android.synthetic.main.fragment_profile.*
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : HambaBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populatePersonTypeDropDown()
        populatePrefixDropDown()
        populateNationalityDropDown()
        populateIdentityDropDown()
        populateCountryDropDown()
        populateAddressTypeDropDown()
        populateInterestDropDown()
        setDatePickListener()
        setSaveButtonListener()

        APiManager.getUserProfile(activity!!, this)
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is GetProfileResponse) {
            if (apiResponse.details != null) {
                updateUI(apiResponse.details!!)
            } else {
                AlertDialogProvider.showAlertDialog(activity!!, apiResponse.message)
            }
        }

        if (apiResponse is IndividualProfileEditResponse) {
            if (apiResponse.success!!) {
                AlertDialogProvider.showAlertDialog(activity!!, getString(R.string.record_updated_successfully))
            } else {
                AlertDialogProvider.showAlertDialog(activity!!, apiResponse.message)
            }
        }
    }

    override fun onApiFailure(errorCode: Int) {
        when (errorCode) {
            HttpErrorCodes.Unauthorized.code -> AlertDialogProvider.showAlertDialog(activity!!, getString(R.string.action_unauthorized))
            HttpErrorCodes.Forbidden.code -> AlertDialogProvider.showAlertDialog(activity!!, getString(R.string.action_forbidden))
            HttpErrorCodes.NotFound.code -> AlertDialogProvider.showAlertDialog(activity!!, getString(R.string.not_found))
            else -> super.onApiFailure(errorCode)
        }
    }

    private fun updateUI(details: GetProfileResponse.Details) {

        swPushNotifications.isChecked = details.enableNotification!!
        editText_phone.isEnabled = false

        if (details.personType != null && details.personType!!.isNotEmpty()) {
            spinner_personType.setSelected(resources.getStringArray(R.array.person_types).indexOf(details.personType))
        }

        if (details.nationality != null && details.nationality!!.isNotEmpty()) {
            spinner_nationality.setSelected(resources.getStringArray(R.array.nationalities).indexOf(details.nationality))
        }

        if (details.registrationType != null && details.registrationType!!.isNotEmpty()) {
            spinner_identity.setSelected(resources.getStringArray(R.array.identities).indexOf(details.registrationType))
        }

        if (details.registrationNo != null && details.registrationNo!!.isNotEmpty()) {
            editText_identityValue.setText(details.registrationNo!!)
        }

        if (details.birthDate != null && details.birthDate!!.isNotEmpty()) {
            editText_dateOfBirth.setText(details.birthDate!!)
        }

        if (details.prefix != null && details.prefix!!.isNotEmpty()) {
            spinner_prefix.setSelected(resources.getStringArray(R.array.prefix_list).indexOf(details.prefix))
        }

        if (details.gender != null && details.gender!!.isNotEmpty()) {
            editText_gender.setGender(details.gender!!)
        }

        if (details.number != null && details.number!!.isNotEmpty()) {
            val phoneNumber = PhoneNumberUtil.createInstance(activity!!).parse(details.number, Constants.EMPTY_STRING)
            editText_phone.setPhoneNumber(phoneNumber.nationalNumber.toString())
            editText_phone.setCountryCode(phoneNumber.countryCode)
        }

        if (details.addressType != null && details.addressType!!.isNotEmpty()) {
            spinner_addressType.setSelected(resources.getStringArray(R.array.address_type).indexOf(details.addressType))
        }

        if (details.address != null && details.address!!.isNotEmpty()) {
            editText_address.setText(details.address!!)
        }

        if (details.cityName != null && details.cityName!!.isNotEmpty()) {
            editText_city.setText(details.cityName!!)
        }

        if (details.country != null && details.country!!.isNotEmpty()) {
            spinner_country.setSelected(resources.getStringArray(R.array.countries).indexOf(details.country))
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
        DatePickerDialog(activity!!, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            onDateSelected(calendar)
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun onDateSelected(calendar: Calendar) {
        if (DateUtils.isToday(calendar.timeInMillis) || calendar.time.after(Date())) {
            AlertDialogProvider.showAlertDialog(activity!!, getString(R.string.please_select_valid_date))
        } else {
            if (HambaUtils.isAgeLessThen18(calendar)) {
                AlertDialogProvider.showAlertDialog(activity!!, getString(R.string.sorry_you_are_under_age))
            } else {
                editText_dateOfBirth.setText(SimpleDateFormat(Constants.DOB_FORMAT).format(calendar.time))
            }
        }
    }

    private fun populatePersonTypeDropDown() {
        spinner_personType.setItems(resources.getStringArray(R.array.person_types))
    }

    private fun populatePrefixDropDown() {
        spinner_prefix.setItems(resources.getStringArray(R.array.prefix_list))
    }

    private fun populateNationalityDropDown() {
        spinner_nationality.setItems(resources.getStringArray(R.array.nationalities))
    }

    private fun populateIdentityDropDown() {
        spinner_identity.setItems(resources.getStringArray(R.array.identities))
    }

    private fun populateCountryDropDown() {
        spinner_country.setItems(resources.getStringArray(R.array.countries))
    }

    private fun populateAddressTypeDropDown() {
        spinner_addressType.setItems(resources.getStringArray(R.array.address_type))
    }

    private fun populateInterestDropDown() {
        spinner_interest.setItems(resources.getStringArray(R.array.interests))
    }

    private fun setSaveButtonListener() {
        btn_save.setOnClickListener {
            if (checkValidations()) {
                APiManager.editIndividualProfileApi(activity!!, this, getIndividualProfileEditRequest())
            }
        }
    }

    private fun getIndividualProfileEditRequest() : IndividualProfileEditRequest {

        val editUserIndividualProfileRequest = IndividualProfileEditRequest()
        editUserIndividualProfileRequest.personType = spinner_personType.text.toString()
        editUserIndividualProfileRequest.prefix = spinner_prefix.text.toString()
        editUserIndividualProfileRequest.firstName = editText_firstName.getText()
        editUserIndividualProfileRequest.lastName = editText_lastName.getText()
        editUserIndividualProfileRequest.gender = editText_gender.getGender()
        editUserIndividualProfileRequest.birthDate = editText_dateOfBirth.getText()
        editUserIndividualProfileRequest.nationality = spinner_nationality.text.toString()
        editUserIndividualProfileRequest.registrationNo = editText_identityValue.getText()
        editUserIndividualProfileRequest.registrationType = spinner_identity.text.toString()
        editUserIndividualProfileRequest.middleName = editText_middleName.getText()
        editUserIndividualProfileRequest.cityName = editText_city.getText()
        editUserIndividualProfileRequest.zipCode = editText_zipCode.getText()
        editUserIndividualProfileRequest.country = spinner_country.text.toString()
        editUserIndividualProfileRequest.email = editText_email.getText()
        editUserIndividualProfileRequest.number = editText_phone.getPhoneNumber()
        editUserIndividualProfileRequest.addressType = spinner_addressType.text.toString()
        editUserIndividualProfileRequest.address = editText_address.getText()
        editUserIndividualProfileRequest.enableNotification = swPushNotifications.isChecked

        //ToDo interest would be a different widget or multi select dropdown
        val selectedInterests = ArrayList<String>()
        selectedInterests.add(spinner_interest.text.toString())
        editUserIndividualProfileRequest.interests = selectedInterests
        editUserIndividualProfileRequest.imgExtension = "some_url"

        return editUserIndividualProfileRequest
    }

    private fun checkValidations(): Boolean {

        if (editText_firstName.getText().isEmpty()) {
            editText_firstName.setError(getString(R.string.please_enter_first_name))
            return false
        }

        if (editText_lastName.getText().isEmpty()) {
            editText_lastName.setError(getString(R.string.please_enter_last_name))
            return false
        }

        if (editText_dateOfBirth.getText().isEmpty()) {
            editText_dateOfBirth.setError(getString(R.string.please_enter_date_of_birth))
            return false
        }

        if (editText_identityValue.getText().isEmpty()) {
            editText_identityValue.setError(getString(R.string.please_enter_registration_number))
            return false
        }

        if (!HambaUtils.isEmailValid(editText_email.getText())) {
            editText_email.setError(getString(R.string.please_enter_valid_email))
            return false
        }

        return true
    }
}