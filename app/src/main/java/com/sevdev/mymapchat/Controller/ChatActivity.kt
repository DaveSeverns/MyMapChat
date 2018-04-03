package com.sevdev.mymapchat.Controller

import android.Manifest
import android.content.*
import android.location.Location
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteCallbackList
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapFragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sevdev.mymapchat.Adapters.RecyclerAdapter
import com.sevdev.mymapchat.Model.Partner
import com.sevdev.mymapchat.R
import com.sevdev.mymapchat.Utility.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

class ChatActivity : AppCompatActivity(), PartnerListFragment.OnParnterListFragmentInteractionListener, PartnerMap.MapFragmentInterface {
    override fun getSortedPartnersListForMap() {
    }

    override fun onFragmentInteraction(uri: Uri) {
    }


    private lateinit var partnerListFragment: PartnerListFragment
    private lateinit var mapFragment : PartnerMap
    private var mDisposable:Disposable? = null
    private var mCompositeDisposable = CompositeDisposable()
    //private var mLocation: Location? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mNetworkManager: NetworkManager
    private var currentLocation: Location? = null
    private lateinit var mKaMorrisClient: KaMorrisClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mNetworkManager = NetworkManager()

        mNetworkManager.checkPermission(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)



        partnerListFragment = PartnerListFragment()
        mapFragment = PartnerMap.newInstance()

        val fragTransaction = fragmentManager.beginTransaction()
        fragTransaction.replace(R.id.single_pane_frame, partnerListFragment).commit()

        add_partner_btn.setOnClickListener(this::addPartner)


    }

    override fun itemClicked(partner: String?) {
        val mapTransaction = fragmentManager.beginTransaction()
        mapTransaction.replace(R.id.single_pane_frame, mapFragment).addToBackStack(null).commit()
        fragmentManager.executePendingTransactions()
    }

    fun addPartner(view: View){
        grabLocation()
        val editText = EditText(this)
        val builder = AlertDialog.Builder(this)
                .setTitle("Username:").setView(editText)
        builder.setPositiveButton("Add", DialogInterface.OnClickListener{dialog, which ->
            val user = editText.text
            val partner = Partner(user.toString(),currentLocation?.latitude.toString(),currentLocation?.longitude.toString(),0f)
            mNetworkManager.postPartnerToServer(partner)
        })

        builder.show()
    }

    override fun onResume() {
        super.onResume()
        mKaMorrisClient = NetworkManager.networkCall()
        mDisposable = updatePartnerListFromServer(mKaMorrisClient)
    }

    override fun onPause() {
        super.onPause()
        mDisposable?.dispose()
    }

    override fun onStart() {
        super.onStart()


    }

    override fun onStop() {
        super.onStop()


    }


    fun grabLocation(): Boolean{
        var locationGrabbed = false
        try {
            mFusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                if (location != null){
                    currentLocation =location
                    locationGrabbed = true
                }

            }
        }catch (exception: SecurityException){
            mNetworkManager.checkPermission(this)
        }
        return locationGrabbed
    }

    fun updatePartnerListFromServer(kaMorrisClient: KaMorrisClient): Disposable{
        return Observable.interval(30,TimeUnit.SECONDS).startWith(0)
                .flatMap { kaMorrisClient.getPartnerList() }
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({partnerList ->
                    partnerListFragment.addPartnersToList(partnerList)
                    mapFragment.markersToMap(partnerList)
                    Log.e("Observable Ran", " See you again in 30 seconds")
                })
    }


}

