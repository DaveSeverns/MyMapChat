package com.sevdev.mymapchat

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ColorSpace
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sevdev.mymapchat.Model.Model
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var locationManager : LocationManager? = null
    private var latitudeAsString : String = ""
    private var longitudeAsString : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //initialize the Realm db
        var createdUser = Model.User()
        enter_name_button.setOnClickListener{
            if(enterName.text != null){
                createdUser.name = enterName.text.toString()
                createdUser.latitude = latitudeAsString
                createdUser.longitude = longitudeAsString
            }
            val intent : Intent = Intent(this,ChatActivity::class.java)
            startActivity(intent)
        }

    }
    private val locationListener : LocationListener = object : LocationListener{
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(provider: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(provider: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onLocationChanged(location: Location?) {
            latitudeAsString = location!!.latitude.toString()
            longitudeAsString =  location!!.longitude.toString()
        }
    }

    @SuppressLint("MissingPermission")
    fun requestUpdates(): Unit{
        this.locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,0L,0f,locationListener)
        this.locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0L,0f,locationListener)
        this.locationManager!!.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,0L,0f,locationListener)

    }

    override fun onResume() {
        super.onResume()
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestUpdates()
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    123)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestUpdates()
        } else {
            Toast.makeText(this, "SOL homie", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager!!.removeUpdates(locationListener)
    }
}
