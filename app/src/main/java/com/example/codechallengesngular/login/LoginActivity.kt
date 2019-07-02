package com.example.codechallengesngular.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.codechallengesngular.R
import com.example.codechallengesngular.base.WeatherActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.Locale


class LoginActivity : AppCompatActivity(), LocationListener {
    private lateinit var viewModel: LoginViewModel

    @BindView(R.id.sign_in_bottom_sheet)
    protected lateinit var bottomSheet: View

    @BindView(R.id.hello)
    protected lateinit var helloText: View

    @BindView(R.id.description)
    protected lateinit var descriptionText: View

    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null
    private var permissions =
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        setupBottomSheet()
    }

    private fun setupBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.peekHeight = 0
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    helloText.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
                    descriptionText.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
                }
            }
        })
        helloText.animate()
            .setStartDelay(FADE_IN_DELAY)
            .alpha(1.0f)
            .setDuration(FADE_IN_DURATION)
            .start()

        descriptionText.run {
            animate()
                .setStartDelay(FADE_IN_DELAY)
                .alpha(1.0f)
                .setDuration(FADE_IN_DURATION)
                .withEndAction {
                    postDelayed(
                        { bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED },
                        BOTTOM_SHEET_EXPAND_DELAY
                    )
                }
                .start()
        }
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            locationGps = location
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(locationGps!!.latitude, locationGps!!.longitude, 1)
            val cityName = addresses.get(0).locality
            locationManager.removeUpdates(this)
            val intent = Intent(applicationContext, WeatherActivity::class.java)
                .putExtra(ACTUAL_CITY, cityName)
            startActivity(intent)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (hasGps || hasNetwork) {

            if (hasGps) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, this)

                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null)
                    locationGps = localGpsLocation
            }
            if (hasNetwork) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, object :
                    LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationNetwork = location
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

                    override fun onProviderEnabled(provider: String?) {}

                    override fun onProviderDisabled(provider: String?) {}

                })

                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (localNetworkLocation != null)
                    locationNetwork = localNetworkLocation
            }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain =
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                            permissions[i]
                        )
                    if (requestAgain) {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Go to settings and enable the permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (allSuccess) {
                getLocation()
            }
        }
    }

    @OnClick(R.id.signin)
    fun onSigninClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {
                getLocation()
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        } else {
            getLocation()
        }
    }
}

private const val FADE_IN_DURATION: Long = 500
private const val FADE_IN_DELAY: Long = 1500
private const val BOTTOM_SHEET_EXPAND_DELAY: Long = 2000
private const val PERMISSION_REQUEST = 10
private const val ACTUAL_CITY = "actual_city"