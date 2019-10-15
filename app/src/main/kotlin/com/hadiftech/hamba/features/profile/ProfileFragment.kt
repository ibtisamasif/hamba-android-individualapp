package com.hadiftech.hamba.features.profile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.Constants
import com.hadiftech.hamba.core.HambaBaseFragment
import com.hadiftech.hamba.core.HambaUtils
import com.hadiftech.hamba.core.enums.DialogTheme
import com.hadiftech.hamba.core.enums.IdentityType
import com.hadiftech.hamba.core.listeners.DialogButtonClickListener
import com.hadiftech.hamba.core.listeners.OnAvatarClickListener
import com.hadiftech.hamba.core.providers.AlertDialogProvider
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.core.services.HttpErrorCodes
import com.hadiftech.hamba.core.session.Session
import com.hadiftech.hamba.core.session.User
import com.hadiftech.hamba.features.login.LoginActivity
import com.hadiftech.hamba.features.profile.edit_profile_service.IndividualProfileEditRequest
import com.hadiftech.hamba.features.profile.edit_profile_service.IndividualProfileEditResponse
import com.hadiftech.hamba.features.profile.get_profile_service.GetProfileResponse
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import kotlinx.android.synthetic.main.fragment_profile.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProfileFragment : HambaBaseFragment(), OnAvatarClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateDefaultAvatar()
        populatePersonTypeDropDown()
        populatePrefixDropDown()
        populateNationalityDropDown()
        populateIdentityDropDown()
        populateCountryDropDown()
        populateAddressTypeDropDown()
        populateInterestDropDown()

        setDatePickListener()
        setSaveButtonListener()
        setEmailAddressTextChangeListener()

        button_TouchHandler.setOnClickListener { closeAvatarSelectionComponent() }
        imageView_profilePic.setOnClickListener { displayAvatarSelectionComponent() }
        avatarSelectionComponent.setOnAvatarClickListener(this)
        avatarSelectionComponent.displayFemaleAvatars()

        if (Session.isSessionAvailable()) {
            updateUI(User.getUserProfile()!!)
        } else {
            showGuestUserAlert()
        }
    }

    private fun showGuestUserAlert(){
        AlertDialogProvider.showAlertDialog(activity!!, DialogTheme.ThemeGreen, getString(R.string.you_are_signed_in_as_guest_please_login),
            getString(R.string.login), object : DialogButtonClickListener {
                override fun onClick(alertDialog: AlertDialog) {
                    alertDialog.dismiss()
                    moveToLoginActivity()
                }
            })
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is IndividualProfileEditResponse) {
            if (apiResponse.success!!) {
                User.setCurrentProfileOutdated(true)
                AlertDialogProvider.showAlertDialog(activity!!, DialogTheme.ThemeGreen, getString(R.string.record_updated_successfully))
            } else {
                AlertDialogProvider.showAlertDialog(activity!!, DialogTheme.ThemeGreen, apiResponse.message)
            }
        }
    }

    override fun onApiFailure(errorCode: Int) {
        when (errorCode) {
            HttpErrorCodes.Unauthorized.code -> AlertDialogProvider.showAlertDialog(activity!!, DialogTheme.ThemeGreen, getString(R.string.action_unauthorized))
            HttpErrorCodes.Forbidden.code -> AlertDialogProvider.showAlertDialog(activity!!, DialogTheme.ThemeGreen, getString(R.string.action_forbidden))
            HttpErrorCodes.NotFound.code -> AlertDialogProvider.showAlertDialog(activity!!, DialogTheme.ThemeGreen, getString(R.string.not_found))
            else -> super.onApiFailure(errorCode)
        }
    }

    override fun onAvatarClicked(resId: Int) {
        imageView_profilePic.setImageResource(resId)
        imageView_profilePic.tag = resources.getResourceEntryName(resId)
    }

    private fun displayAvatarSelectionComponent() {
        var slideInAnimation = AnimationUtils.loadAnimation(activity, R.anim.avatar_slide_in_from_left)
        button_TouchHandler.visibility = View.VISIBLE
        avatarSelectionComponent.visibility = View.VISIBLE
        avatarSelectionComponent.startAnimation(slideInAnimation)
    }

    private fun closeAvatarSelectionComponent() {
        var slideOutAnimation = AnimationUtils.loadAnimation(activity, R.anim.avatar_slide_out_to_left)
        avatarSelectionComponent.startAnimation(slideOutAnimation)
        slideOutAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                button_TouchHandler.visibility = View.GONE
                avatarSelectionComponent.visibility = View.GONE
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    private fun updateUI(details: GetProfileResponse.Details) {

        swPushNotifications.isChecked = details.enableNotification!!
        swShowMyMobileNumber.isChecked = details.enableNumberVisibility!!
        swOthersCanSeeMyAge.isChecked = details.enableAgeVisibility!!

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
            var resId = resources.getIdentifier(details.avatar, "drawable", activity!!.packageName)
            if (resId == 0) {
                imageView_profilePic.setImageResource(R.drawable.female_avatar_1)
                imageView_profilePic.tag = resources.getResourceEntryName(R.drawable.female_avatar_1)
            } else {
                imageView_profilePic.setImageResource(resId)
                imageView_profilePic.tag = resources.getResourceEntryName(resId)
            }
        }

        if (!details.interests.isNullOrEmpty()) {
            var listOfInterests: ArrayList<Int> = ArrayList()
            details!!.interests!!.forEach {
                listOfInterests.add(resources.getStringArray(R.array.interests).indexOf(it))
            }
            spinner_interest.setSelected(listOfInterests)
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
            AlertDialogProvider.showAlertDialog(activity!!, DialogTheme.ThemeGreen, getString(R.string.please_select_valid_date))
        } else {
            if (HambaUtils.isAgeLessThen18(calendar)) {
                AlertDialogProvider.showAlertDialog(activity!!, DialogTheme.ThemeGreen, getString(R.string.sorry_you_are_under_age))
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
        spinner_identity.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                when (spinner_identity.text.toString()) {
                    IdentityType.NationalId.value -> editText_identityValue.setInputType(InputType.TYPE_CLASS_NUMBER)
                    IdentityType.Passport.value -> editText_identityValue.setInputType(InputType.TYPE_CLASS_TEXT)
                }
            }
        })
    }

    private fun populateDefaultAvatar(){
        imageView_profilePic.setImageResource(R.drawable.female_avatar_1)
        imageView_profilePic.tag = resources.getResourceEntryName(R.drawable.female_avatar_1)
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

    private fun setEmailAddressTextChangeListener() {
        editText_email!!.setTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //TODO: Do Email OTP Code Verification
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
        editUserIndividualProfileRequest.avatar = imageView_profilePic.tag.toString()
        editUserIndividualProfileRequest.personType = spinner_personType.selected
        editUserIndividualProfileRequest.prefix = spinner_prefix.selected
        editUserIndividualProfileRequest.firstName = editText_firstName.getText()
        editUserIndividualProfileRequest.lastName = editText_lastName.getText()
        editUserIndividualProfileRequest.gender = editText_gender.getGender()
        editUserIndividualProfileRequest.birthDate = editText_dateOfBirth.getText()
        editUserIndividualProfileRequest.nationality = spinner_nationality.selected
        editUserIndividualProfileRequest.registrationNo = editText_identityValue.getText()
        editUserIndividualProfileRequest.registrationType = spinner_identity.selected
        editUserIndividualProfileRequest.middleName = editText_middleName.getText()
        editUserIndividualProfileRequest.cityName = editText_city.getText()
        editUserIndividualProfileRequest.zipCode = editText_zipCode.getText()
        editUserIndividualProfileRequest.country = spinner_country.selected
        editUserIndividualProfileRequest.addressType = spinner_addressType.selected
        editUserIndividualProfileRequest.address = editText_address.getText()
        editUserIndividualProfileRequest.enableNotification = swPushNotifications.isChecked
        editUserIndividualProfileRequest.enableNumberVisibility = swShowMyMobileNumber.isChecked
        editUserIndividualProfileRequest.enableAgeVisibility = swOthersCanSeeMyAge.isChecked
        editUserIndividualProfileRequest.imgExtension = "http://profile_image_url"
        editUserIndividualProfileRequest.interests = spinner_interest.multipleSelected

        var existingEmail = User.getUserProfile()!!.email
        if (existingEmail != null && existingEmail.isNotEmpty()) {
            editUserIndividualProfileRequest.email = existingEmail
        } else {
            editUserIndividualProfileRequest.email = editText_email.getText()
        }

        var existingNumber = User.getUserProfile()!!.number
        if (existingNumber != null && existingNumber.isNotEmpty()) {
            editUserIndividualProfileRequest.number = existingNumber
        } else {
            editUserIndividualProfileRequest.number = editText_phone.getPhoneNumber()
        }

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

        if (editText_dateOfBirth.getText().isEmpty() || editText_dateOfBirth.getText().length < 4) {
            editText_dateOfBirth.setError(getString(R.string.please_select_date_of_birth))
            return false
        }

        if (editText_identityValue.getText().isEmpty()) {
            if (spinner_identity.text.toString() == IdentityType.Passport.name) {
                editText_identityValue.setError(getString(R.string.please_enter_passport_number))
            } else {
                editText_identityValue.setError(getString(R.string.please_enter_national_id_number))
            }
            return false
        }

        if (editText_city.getText().isEmpty()) {
            editText_city.setError(getString(R.string.please_enter_city_name))
            return false
        }

        if (editText_email.getText().isEmpty()) {
            editText_email.setError(getString(R.string.please_enter_email))
            return false
        }

        if (!HambaUtils.isEmailValid(editText_email.getText())) {
            editText_email.setError(getString(R.string.please_enter_valid_email))
            return false
        }

        if (editText_phone.getPhoneNumber().length < 10) {
            editText_phone.setError(getString(R.string.please_enter_valid_cell_number))
            return false
        }

        return true
    }

    private fun moveToLoginActivity() {
        val loginIntent = Intent(activity!!, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(loginIntent)
        activity!!.finish()
    }
}
