package com.hadiftech.hamba.features.profile

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.HambaBaseFragment
import com.hadiftech.hamba.core.HambaUtils
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.core.services.HttpErrorCodes
import com.hadiftech.hamba.core.session.Session
import com.hadiftech.hamba.core.views.HambaProfileEditText
import com.hadiftech.hamba.features.login.LoginActivity
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

        setFirstNameTextListener()
        setMiddleNameTextListener()
        setLastNameTextListener()
        setIdentityValueTextListener()
        setCityTextListener()
        setZipCodeTextListener()
        setAddressTextListener()

        if (Session.isSessionAvailable()) {
            APiManager.getUserProfile(activity!!, this)
        } else {
            AlertDialogProvider.showAlertDialog(activity!!, getString(R.string.you_are_signed_in_as_guest_please_login), getString(R.string.login), DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                moveToLoginActivity()
            })
        }
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

        swShowMyMobileNumber.isChecked = details.enableNumberVisibility!!

        swOthersCanSeeMyAge.isChecked = details.enableAgeVisibility!!

        editText_phone.isEnabled = false

        if (details.personType != null && details.personType!!.isNotEmpty()) {
            spinner_personType.setSelection(resources.getStringArray(R.array.person_types).indexOf(details.personType))
        }

        if (details.nationality != null && details.nationality!!.isNotEmpty()) {
            spinner_nationality.setSelection(resources.getStringArray(R.array.nationalities).indexOf(details.nationality))
        }

        if (details.registrationType != null && details.registrationType!!.isNotEmpty()) {
            spinner_identity.setSelection(resources.getStringArray(R.array.identities).indexOf(details.registrationType))
        }

        if (details.registrationNo != null && details.registrationNo!!.isNotEmpty()) {
            editText_identityValue.setText(details.registrationNo!!)
        }

        if (details.birthDate != null && details.birthDate!!.isNotEmpty()) {
            editText_dateOfBirth.setText(details.birthDate!!)
        }

        if (details.prefix != null && details.prefix!!.isNotEmpty()) {
            spinner_prefix.setSelection(resources.getStringArray(R.array.prefix_list).indexOf(details.prefix))
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
            editText_dateOfBirth.setText(SimpleDateFormat(Constants.DOB_FORMAT).format(calendar.time))
        }
    }

    private fun populatePersonTypeDropDown() {
        spinner_personType.populate(activity!!, R.array.person_types)
    }

    private fun populatePrefixDropDown() {
        spinner_prefix.populate(activity!!, R.array.prefix_list)
    }

    private fun populateNationalityDropDown() {
        spinner_nationality.populate(activity!!, R.array.nationalities)
    }

    private fun populateIdentityDropDown() {
        spinner_identity.populate(activity!!, R.array.identities)
        spinner_identity.onItemSelectedListener = OnSpinnerIdentityItemSelectedListener(editText_identityValue)
    }

    private fun populateCountryDropDown() {
        spinner_country.populate(activity!!, R.array.countries)
    }

    private fun populateAddressTypeDropDown() {
        spinner_addressType.populate(activity!!, R.array.address_type)
    }

    private fun populateInterestDropDown() {
        spinner_interest.populate(activity!!, R.array.interests)
    }

    private fun setFirstNameTextListener() {
        editText_firstName!!.setTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (editText_firstName!!.getText().isNotEmpty()) {
                    editText_firstName!!.setError(null)
                }
                if (editText_firstName!!.getText().isNotEmpty() && editText_firstName!!.getText().length > 9) {
                    editText_firstName!!.setError("10 characters only")
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun setMiddleNameTextListener() {
        editText_middleName!!.setTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (editText_middleName!!.getText().isNotEmpty()) {
                    editText_middleName!!.setError(null)
                }
                if (editText_middleName!!.getText().isNotEmpty() && editText_middleName!!.getText().length > 9) {
                    editText_middleName!!.setError("10 characters only")
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun setLastNameTextListener() {
        editText_lastName!!.setTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (editText_lastName!!.getText().isNotEmpty()) {
                    editText_lastName!!.setError(null)
                }
                if (editText_lastName!!.getText().isNotEmpty() && editText_lastName!!.getText().length > 9) {
                    editText_lastName!!.setError("10 characters only")
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun setIdentityValueTextListener() {
        editText_identityValue!!.setTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (editText_identityValue!!.getText().isNotEmpty()) {
                    editText_identityValue!!.setError(null)
                }
                if (editText_identityValue!!.getText().isNotEmpty() && editText_identityValue!!.getText().length > 19) {
                    editText_identityValue!!.setError("20 characters only")
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun setCityTextListener() {
        editText_city!!.setTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (editText_city!!.getText().isNotEmpty()) {
                    editText_city!!.setError(null)
                }
                if (editText_city!!.getText().isNotEmpty() && editText_city!!.getText().length > 9) {
                    editText_city!!.setError("10 characters only")
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun setZipCodeTextListener() {
        editText_zipCode!!.setTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (editText_zipCode!!.getText().isNotEmpty()) {
                    editText_zipCode!!.setError(null)
                }
                if (editText_zipCode!!.getText().isNotEmpty() && editText_zipCode!!.getText().length > 9) {
                    editText_zipCode!!.setError("10 characters only")
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun setAddressTextListener() {
        editText_address!!.setTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (editText_address!!.getText().isNotEmpty()) {
                    editText_address!!.setError(null)
                }
                if (editText_address!!.getText().isNotEmpty() && editText_address!!.getText().length > 49) {
                    editText_address!!.setError("50 characters only")
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun setSaveButtonListener() {
        btn_save.setOnClickListener {
            if (checkValidations()) {
                APiManager.editIndividualProfileApi(activity!!, this, getIndividualProfileEditRequest())
            }
        }
    }

    private fun getIndividualProfileEditRequest(): IndividualProfileEditRequest {

        val editUserIndividualProfileRequest = IndividualProfileEditRequest()
        editUserIndividualProfileRequest.personType = spinner_personType.selectedItem.toString()
        editUserIndividualProfileRequest.prefix = spinner_prefix.selectedItem.toString()
        editUserIndividualProfileRequest.firstName = editText_firstName.getText()
        editUserIndividualProfileRequest.lastName = editText_lastName.getText()
        editUserIndividualProfileRequest.gender = editText_gender.getGender()
        editUserIndividualProfileRequest.birthDate = editText_dateOfBirth.getText()
        editUserIndividualProfileRequest.nationality = spinner_nationality.selectedItem.toString()
        editUserIndividualProfileRequest.registrationNo = editText_identityValue.getText()
        editUserIndividualProfileRequest.registrationType = spinner_identity.selectedItem.toString()
        editUserIndividualProfileRequest.middleName = editText_middleName.getText()
        editUserIndividualProfileRequest.cityName = editText_city.getText()
        editUserIndividualProfileRequest.zipCode = editText_zipCode.getText()
        editUserIndividualProfileRequest.country = spinner_country.selectedItem.toString()
        editUserIndividualProfileRequest.email = editText_email.getText()
        editUserIndividualProfileRequest.number = editText_phone.getPhoneNumber()
        editUserIndividualProfileRequest.addressType = spinner_addressType.selectedItem.toString()
        editUserIndividualProfileRequest.address = editText_address.getText()
        editUserIndividualProfileRequest.enableNotification = swPushNotifications.isChecked
        editUserIndividualProfileRequest.enableNumberVisibility = swShowMyMobileNumber.isChecked
        editUserIndividualProfileRequest.enableAgeVisibility = swOthersCanSeeMyAge.isChecked

        //ToDo interest would be a different widget or multi select dropdown
        val selectedInterests = ArrayList<String>()
        selectedInterests.add(spinner_interest.selectedItem.toString())
        editUserIndividualProfileRequest.interests = selectedInterests
        editUserIndividualProfileRequest.imgExtension = "some_url"

        return editUserIndividualProfileRequest
    }

    private fun checkValidations(): Boolean {

        if (editText_firstName.getText().isEmpty() || editText_firstName.getText().length < 4) {
            editText_firstName.setError(getString(R.string.field_required_minimum_4_characters))
            return false
        }

        if (editText_lastName.getText().isEmpty() || editText_lastName.getText().length < 4) {
            editText_lastName.setError(getString(R.string.field_required_minimum_4_characters))
            return false
        }

        if (editText_dateOfBirth.getText().isEmpty() || editText_dateOfBirth.getText().length < 4) {
            editText_dateOfBirth.setError(getString(R.string.field_required_minimum_4_characters))
            return false
        }

        if (editText_identityValue.getText().isEmpty() || editText_identityValue.getText().length < 4) {
            editText_identityValue.setError(getString(R.string.field_required_minimum_4_characters))
            return false
        }

        if (editText_city.getText().isEmpty() || editText_city.getText().length < 4) {
            editText_city.setError(getString(R.string.field_required_minimum_4_characters))
            return false
        }

        if (!HambaUtils.isEmailValid(editText_email.getText())) {
            editText_email.setError(getString(R.string.please_enter_valid_email))
            return false
        }

        return true
    }

    private fun moveToLoginActivity() {
        Session.clearSession()
        val loginIntent = Intent(activity!!, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(loginIntent)
        activity!!.finish()
    }
}

class OnSpinnerIdentityItemSelectedListener(private var editText_identityValue: HambaProfileEditText) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> editText_identityValue.setInputType(InputType.TYPE_CLASS_NUMBER)
            1 -> editText_identityValue.setInputType(InputType.TYPE_CLASS_TEXT)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}
