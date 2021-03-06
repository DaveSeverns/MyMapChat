package com.sevdev.mymapchat.Controller


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sevdev.mymapchat.Model.Partner

import com.sevdev.mymapchat.R



/**
 * A simple [Fragment] subclass.
 *
 */
class PartnerMap : MapFragment(), OnMapReadyCallback {

    private lateinit var mMapFragmentInterface: MapFragmentInterface
    private  var mMapView: MapView? = null
    private var mParnters : ArrayList<Partner>? = null

    override fun onMapReady(googleMap: GoogleMap?) {
        val templeU = LatLng(39.9813235,-75.1541054)
        googleMap!!.addMarker(MarkerOptions().position(templeU).title("Temple University"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(templeU))
    }

    interface MapFragmentInterface{
        fun getSortedPartnersListForMap():ArrayList<Partner>
    }

    companion object {
        fun newInstance(): PartnerMap{
            return PartnerMap()
        }
    }


    override fun onAttach(p0: Activity?) {
        super.onAttach(p0)
        mMapFragmentInterface = p0 as MapFragmentInterface
    }

    override fun onPause() {
        super.onPause()
        mMapView?.onPause()
    }

    override fun setArguments(p0: Bundle?) {
        super.setArguments(p0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_partner_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.contactFrag_F_map) as MapFragment
        mapFragment.getMapAsync(this)
        mMapView = view?.findViewById<MapView>(R.id.map_view)!!
        mMapView?.onCreate(savedInstanceState)
        mParnters = mMapFragmentInterface.getSortedPartnersListForMap()
        markersToMap(mParnters)
        return view
    }

    fun markersToMap(partnerMarkers: ArrayList<Partner>?){
        if (partnerMarkers != null){
            mMapView?.getMapAsync {
                for (user in partnerMarkers){
                    val markerOptions = MarkerOptions()
                    it.addMarker(markerOptions.position(LatLng(user.latitude!!.toDouble(),user.longitude!!.toDouble())))
                }
            }
        }
        Log.e("Map Marker", partnerMarkers.toString())

    }




    override fun onLowMemory() {
        super.onLowMemory()
        mMapView?.onLowMemory()
    }

    override fun onStart() {
        super.onStart()

        mMapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
    }

    override fun onSaveInstanceState(p0: Bundle?) {
        super.onSaveInstanceState(p0)
        mMapView?.onSaveInstanceState(p0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMapView?.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        mMapView?.onStop()
    }

    override fun getMapAsync(p0: OnMapReadyCallback?) {
        super.getMapAsync(p0)

    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView?.onDestroy()
    }


}
