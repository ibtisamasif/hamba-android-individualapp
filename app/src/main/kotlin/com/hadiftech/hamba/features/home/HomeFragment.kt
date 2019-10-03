package com.hadiftech.hamba.features.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.location.*
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.hadiftech.hamba.R
import com.hadiftech.hamba.core.HambaBaseFragment
import com.hadiftech.hamba.core.services.APiManager
import com.hadiftech.hamba.core.services.HambaBaseApiResponse
import com.hadiftech.hamba.core.session.Session
import com.hadiftech.hamba.core.session.User
import com.hadiftech.hamba.features.profile.get_profile_service.GetProfileResponse
import kotlinx.android.synthetic.main.fragment_home.*
import mumayank.com.airlocationlibrary.AirLocation
import java.util.*
import com.hadiftech.hamba.core.enums.DialogTheme
import com.hadiftech.hamba.core.listeners.DialogButtonClickListener
import com.hadiftech.hamba.core.providers.AlertDialogProvider

class HomeFragment : HambaBaseFragment() {

    private var airLocation: AirLocation? = null
    private val LOCATION_PERMISSION_GRANTED: Int = 1111
    private val GPS_ENABLE_REQUEST: Int = 2222

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bannerAnimation()
        llTopDealsHorizontalScroll.animation = topDealsAnimation()
        checkCurrentLocationPermissions(true)
    }

    private fun checkCurrentLocationPermissions(displayAlert: Boolean) {
        if ((ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            && (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {

            checkIsGpsEnabled(displayAlert)
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_GRANTED)
        }
    }

    private fun checkIsGpsEnabled(displayAlert: Boolean) {

        val locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            fetchCurrentLocation()
        } else {
            if (displayAlert) {
                displayGpsAlert()
            } else {
                textView_currentLocation.text = getString(R.string.gps_not_enabled)
            }
        }
    }

    private fun displayGpsAlert() {
        AlertDialogProvider.showAlertDialog(context!!, DialogTheme.ThemeWhite, getString(R.string.enable_gps_description), getString(R.string.yes),
            object : DialogButtonClickListener {
                override fun onClick(alertDialog: AlertDialog) {
                    alertDialog.dismiss()
                    startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_ENABLE_REQUEST)
                }
            },
            object : DialogButtonClickListener {
                override fun onClick(alertDialog: AlertDialog) {
                    alertDialog.dismiss()
                    textView_currentLocation.text = getString(R.string.gps_not_enabled)
                }
            })
    }

    private fun fetchCurrentLocation() {
        airLocation = AirLocation(activity as Activity,
            shouldWeRequestPermissions = false,
            shouldWeRequestOptimization = false,
            callbacks = object: AirLocation.Callbacks {

                override fun onSuccess(location: Location) {
                    displayLocation(location)
                }

                override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                    textView_currentLocation.text = getString(R.string.location_not_found)
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GPS_ENABLE_REQUEST -> {
                checkCurrentLocationPermissions(false)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            LOCATION_PERMISSION_GRANTED -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkIsGpsEnabled(true)
                } else {
                    textView_currentLocation.text = getString(R.string.location_permission_rejected)
                }
            }
        }
    }

    private fun displayLocation(location: Location?) {

        if (location != null) {
            val geoCoder = Geocoder(activity, Locale.getDefault())
            val addressList = geoCoder.getFromLocation(location!!.latitude, location!!.longitude, 1)

            if (addressList.isNotEmpty()) {
                var address = addressList[0]
                textView_currentLocation.text = address.subLocality + ", " + address.subAdminArea + ", " + address.countryCode
            } else {
                textView_currentLocation.text = getString(R.string.location_not_found)
            }
        } else {
            textView_currentLocation.text = getString(R.string.location_not_found)
        }
    }

    override fun onResume() {
        super.onResume()

        if (Session.isSessionAvailable() && User.isCurrentProfileOutdated()) {
            APiManager.getUserProfile(activity!!, this)
        } else {
            textView_profilePercentage.text = String.format(getString(R.string._30_complete), User.getProfileUpdatePercentage())
        }
    }

    override fun onApiSuccess(apiResponse: HambaBaseApiResponse) {
        super.onApiSuccess(apiResponse)

        if (apiResponse is GetProfileResponse) {
            if (apiResponse.details != null) {
                User.saveUserProfile(apiResponse.details!!)
                textView_profilePercentage.text = String.format(getString(R.string._30_complete), User.getProfileUpdatePercentage())
            }
        }
    }

    private fun bannerAnimation(){
        val animation = AnimationDrawable()
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_1)!!, 2000)
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_2)!!, 2500)
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_3)!!, 3000)
        animation.isOneShot = false

        imageView_banner.setBackgroundDrawable(animation)
        animation.start()
    }

    private fun topDealsAnimation() : Animation{

        val translateAnimation: Animation = TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE,
            1000f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f)

        translateAnimation.duration = 10000
        translateAnimation.repeatCount = -1
        translateAnimation.repeatMode = Animation.REVERSE
        translateAnimation.interpolator = LinearInterpolator()

        return translateAnimation
    }
}