package com.example.facespace

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private var mMap: GoogleMap? = null
    private var goLon: Double = 0.0
    private var goLat: Double = 0.0
    private var lastLong: Double = 0.0
    private var lastLat: Double = 0.0
    internal var mCurrLocationMarker: Marker? = null
    internal var mGoogleApiClient: GoogleApiClient? = null
    internal lateinit var mLocationRequest: LocationRequest
    // private var which = ""
    private var city:String = "veli"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        supportActionBar?.hide()

        val extras = intent.extras
        if (extras != null) {
            goLon = extras.getDouble("lon").toDouble()
            goLat = extras.getDouble("lat").toDouble()
            //The key argument here must match that used in the other activity
        }






        val mapFragment = supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val btn = findViewById<Button>(R.id.btnCancel)
        btn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("city", city)
            intent.putExtra("lon", lastLong)
            intent.putExtra("lat", lastLat)
            setResult(RESULT_OK, intent)
            finish()

        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.uiSettings.isZoomControlsEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) ==  PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled=true
            }
        } else {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled=true

        }


        var ist = LatLng(41.01, 28.97)
        if (goLat!=0.0 || goLon!=0.0) {
            ist = LatLng(goLat, goLon)
        }
        val markerOption = MarkerOptions().position(ist).title("Current Location")
            .snippet(getAddress(ist.latitude, ist.longitude)).draggable(true)
        mMap!!.animateCamera(CameraUpdateFactory.newLatLng(ist))
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(ist, 15f))
        mCurrLocationMarker = mMap!!.addMarker(markerOption)
        mCurrLocationMarker?.showInfoWindow()

        mMap!!.setOnMarkerDragListener(object: GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: Marker) {
            }

            override fun onMarkerDragEnd(p0: Marker) {



                val newLatLng = LatLng(p0.position.latitude, p0.position.longitude)
                updateMarker(newLatLng)

            }

            override fun onMarkerDragStart(p0: Marker) {
            }
        })
        mMap!!.setOnMapClickListener (object: GoogleMap.OnMapClickListener {
            override fun onMapClick(p0: LatLng) {

                val newLatLng = LatLng(p0.latitude, p0.longitude)

                updateMarker(newLatLng)

            }

        })


    }

    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }

    override fun onLocationChanged(location: Location) {
        /*
        Toast.makeText(this, "he valla geldi elime", Toast.LENGTH_SHORT).show()
        mLastLocation = location
        lastLat = location.latitude
        lastLong = location.longitude
        if (mCurrLocationMarker != null){
            mCurrLocationMarker!!.remove()
        }
        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mCurrLocationMarker = mMap!!.addMarker(markerOptions)

        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.moveCamera(CameraUpdateFactory.zoomTo(11f))

        if (mGoogleApiClient != null){
            LocationServices.getFusedLocationProviderClient(this)


        }

         */
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            LocationServices.getFusedLocationProviderClient(this)
        }
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    fun searchLocation(view: View){
        /*
            if (mCurrLocationMarker != null){
            mCurrLocationMarker!!.remove()
        }
         */
        mCurrLocationMarker?.hideInfoWindow()
        val locationSearch: EditText = findViewById(R.id.et_search)
        var location: String = locationSearch.text.toString().trim()
        var addressList: List<Address>? = null

        if (location == null || location == ""){
            Toast.makeText(this, "Please provide location.", Toast.LENGTH_SHORT).show()
        }else{
            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)
            }catch (e: IOException){
                Toast.makeText(this, "Please provide correct location.", Toast.LENGTH_SHORT).show()
            }

            val address = addressList!![0]
            // mLastLocation.latitude = address.latitude
            // mLastLocation.longitude = address.longitude

            lastLat = address.latitude
            lastLong = address.longitude
            val latLng = LatLng(address.latitude, address.longitude)
            val snippet = getAddress(latLng.latitude, latLng.longitude)
            mCurrLocationMarker?.position  = latLng
            mCurrLocationMarker?.snippet = snippet
            mCurrLocationMarker?.title = "New Location"
            mCurrLocationMarker?.showInfoWindow()
            // mMap!!.addMarker(MarkerOptions().position(latLng).draggable(true).title("location")
            //    .snippet(getAddress(latLng.latitude, latLng.longitude)))
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        }
    }

    private fun updateMarker(latlong:LatLng) {
        mCurrLocationMarker?.hideInfoWindow()
        val geoCoder = Geocoder(this)
        val addresses = geoCoder.getFromLocation(latlong.latitude, latlong.longitude, 1)[0]
        // val markerOption = MarkerOptions().position(latlong).title("Current Loc")
        // .snippet(getAddress(latlong.latitude, latlong.longitude)).draggable(true)
        val snippet = getAddress(latlong.latitude, latlong.longitude)
        lastLat = latlong.latitude
        lastLong = latlong.longitude
        mCurrLocationMarker?.position = latlong
        mCurrLocationMarker?.snippet = snippet
        mCurrLocationMarker?.title = "New Location"
        mCurrLocationMarker?.showInfoWindow()
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15f))
        Toast.makeText(this, "$snippet", Toast.LENGTH_LONG).show()

        // mMap?.addMarker(markerOption)
    }

    private fun getAddress(lat:Double, lon:Double):String? {
        val geoCoder = Geocoder(this)
        val addresses = geoCoder.getFromLocation(lat, lon, 1)
        city = addresses[0].adminArea
        return addresses[0].thoroughfare + "/" + addresses[0].subAdminArea + "/" + addresses[0].adminArea

    }




}
