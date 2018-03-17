package com.sevdev.mymapchat.Controller

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng

import com.sevdev.mymapchat.R
import kotlinx.android.synthetic.main.fragment_partner_map.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PartnerMapFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class PartnerMapFragment :Fragment(), OnMapReadyCallback {


    private var mListener: OnMapFragmentInteractionListener? = null
    private lateinit var mGoogleMap : GoogleMap
    private var partnerMap : MapView? = null
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private var currentLocation : Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        val latlng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
        val cameraUpdate : CameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng,16f)
        mGoogleMap.animateCamera(cameraUpdate)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_partner_map, container, false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        partnerMap = view?.findViewById(R.id.partnerMap) as MapView

        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0f,locationListener )
            currentLocation = fusedLocationClient.lastLocation.result
        }catch(exception : SecurityException){

        }

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        partnerMap?.onCreate(savedInstanceState)
        partnerMap?.onResume()
        partnerMap?.getMapAsync(this)


        locationListener = object : LocationListener{
            override fun onLocationChanged(location: Location?) {

                currentLocation = Location(location)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onProviderDisabled(provider: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onProviderEnabled(provider: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    override fun onStart() {
        super.onStart()
        partnerMap?.onStart()
    }

    override fun onStop() {
        super.onStop()
        partnerMap?.onStop()
    }

    override fun onResume() {
        super.onResume()
        partnerMap?.onResume()
    }

    override fun onPause() {
        super.onPause()
        partnerMap?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        partnerMap?.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        partnerMap?.onDestroy()
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMapFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnMapFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }


}// Required empty public constructor
