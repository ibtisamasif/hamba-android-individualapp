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
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
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
import java.io.IOException

class HomeFragment : HambaBaseFragment() {

    private val bannerImagesArray: IntArray = intArrayOf(R.drawable.top_deals_image_1, R.drawable.top_deals_image_2, R.drawable.top_deals_image_3,
        R.drawable.top_deals_image_1, R.drawable.top_deals_image_2)

    object AnimationConstants {
        const val BannerDisplayDuration = 3000
        const val BannerFadeDuration = 1000
        const val PromoCardsTransitionDuration = 10000L
    }

    private var airLocation: AirLocation? = null
    private val LOCATION_PERMISSION_GRANTED: Int = 1111
    private val GPS_ENABLE_REQUEST: Int = 2222

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPromoCardsToDisplay()
        bannerAnimation()
        linearLayout_promoCardsContainer.animation = topDealsAnimation()
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
            try {
                val geoCoder = Geocoder(activity, Locale.getDefault())
                val addressList = geoCoder.getFromLocation(location!!.latitude, location!!.longitude, 1)

                if (addressList.isNotEmpty()) {
                    var addressLine = addressList[0].getAddressLine(0)
                    if (addressLine != null && addressLine.isNotEmpty()) {
                        textView_currentLocation.text = addressLine
                        return
                    }
                }
            } catch (e: IOException) {}
        }

        textView_currentLocation.text = getString(R.string.location_not_found)
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
                User.setCurrentProfileOutdated(false)
                User.saveUserProfile(apiResponse.details!!)
                textView_profilePercentage.text = String.format(getString(R.string._30_complete), User.getProfileUpdatePercentage())
            }
        }
    }

    private fun addPromoCardsToDisplay() {
        linearLayout_promoCardsContainer.removeAllViews()
        bannerImagesArray.forEach {
            addPromoCard(it)
        }
    }

    private fun addPromoCard(resId: Int) {
        var promoCardImageView = ImageView(context)
        promoCardImageView.scaleType = ImageView.ScaleType.FIT_XY
        promoCardImageView.setImageResource(resId)

        var cardParams = LinearLayout.LayoutParams(
            context!!.resources.getDimensionPixelSize(R.dimen._80sdp),
            context!!.resources.getDimensionPixelSize(R.dimen._38sdp)
        )
        cardParams.marginStart = context!!.resources.getDimensionPixelSize(R.dimen._5sdp)

        var cardView = CardView(context!!)
        cardView.layoutParams = cardParams
        cardView.cardElevation = resources.getDimension(R.dimen._5sdp)
        cardView.radius = resources.getDimension(R.dimen._8sdp)
        cardView.addView(promoCardImageView)

        linearLayout_promoCardsContainer.addView(cardView)
    }

    private fun bannerAnimation(){
        val animation = AnimationDrawable()
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_1)!!, AnimationConstants.BannerDisplayDuration)
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_2)!!, AnimationConstants.BannerDisplayDuration)
        animation.addFrame(ContextCompat.getDrawable(context!!, R.drawable.top_deals_image_3)!!, AnimationConstants.BannerDisplayDuration)
        animation.setEnterFadeDuration(AnimationConstants.BannerFadeDuration)
        animation.setExitFadeDuration(AnimationConstants.BannerFadeDuration)
        animation.isOneShot = false

        imageView_banner.setBackgroundDrawable(animation)
        animation.start()
    }

    private fun topDealsAnimation() : Animation{

        val translateAnimation: Animation = TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE,
            1000f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f)

        translateAnimation.duration = AnimationConstants.PromoCardsTransitionDuration
        translateAnimation.repeatCount = Animation.INFINITE
        translateAnimation.repeatMode = Animation.REVERSE
        translateAnimation.interpolator = LinearInterpolator()

        return translateAnimation
    }
}