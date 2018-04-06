package com.sevdev.mymapchat.Controller

import android.Manifest
import android.content.*
import android.location.Location
import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
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
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URL
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.BadPaddingException
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity(), PartnerListFragment.OnParnterListFragmentInteractionListener, PartnerMap.MapFragmentInterface,NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {
    override fun createNdefMessage(event: NfcEvent?): NdefMessage {
        val sharedPreferences = getSharedPreferences(PARTNER_FILE,Context.MODE_PRIVATE)

        var userName = sharedPreferences.getString("username","def")
        var pubKey = sharedPreferences.getString("publicKey","def_key")
        val records = arrayOfNulls<NdefRecord>(2)
        val ndefRecordBody: NdefRecord




        records[0] = NdefRecord.createMime("text/plain", userName.toByteArray())
        records[1] = NdefRecord.createMime("text/plain", pubKey.toByteArray())

        val message: NdefMessage


        message = NdefMessage(records)

        return message
    }

    override fun onNdefPushComplete(event: NfcEvent?) {
    }

    override fun getSortedPartnersListForMap(): ArrayList<Partner> {
        return mArrayListOfPartners
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
    private var myCrytoUtil = MyCrytoUtil(this)



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
        val sharedPreferences = getSharedPreferences(PARTNER_FILE,Context.MODE_PRIVATE)
        //partner is the key, but there pub key will be the value, not confusing at all.
        var savedPartnerKey = sharedPreferences.getString(partner, DEF_PARTNER_KEY)
        if (savedPartnerKey.equals(DEF_PARTNER_KEY)){
            val keyBuilder = AlertDialog.Builder(this)
                    .setTitle("Get Partner's Key With NFC Bump")
            keyBuilder.setPositiveButton("OK.", DialogInterface.OnClickListener{dialog, which ->
               dialog.dismiss()
            })
            keyBuilder.show()
        }else{
            val messengerIntent = Intent(this,MessengerActivity::class.java)
            messengerIntent.putExtra("username",partner)
            startActivity(messengerIntent)
        }

    }

    fun addPartner(view: View){
        grabLocation()
        val preferences = this.getSharedPreferences(com.sevdev.mymapchat.Utility.PARTNER_FILE,Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val editText = EditText(this)
        val builder = AlertDialog.Builder(this)
                .setTitle("Username:").setView(editText)
        builder.setPositiveButton("Add", DialogInterface.OnClickListener{dialog, which ->
            val user = editText.text
            editor.putString("username",user.toString())
            genPublicNPrivateKey()
            editor.apply()
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
                    mArrayListOfPartners.addAll(partnerList)
                    Log.e("Observable Ran", " See you again in 30 seconds")
                })
    }

    //Static reference to my list of parters can be updated and sent between the fragments
    companion object {
        var mArrayListOfPartners = ArrayList<Partner>()
    }

    fun genPublicNPrivateKey(){
        val cursor = contentResolver.query(Uri.parse(PROVIDER_URI),
                null,
                null,
                null,
                null)
        cursor.moveToNext()
        val pubKey = cursor.getString(0)
        val privateKey = cursor.getString(1)

        val sharedPreferences = getSharedPreferences(PARTNER_FILE,Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("privateKey",privateKey)
        editor.putString("publicKey", pubKey)
        editor.apply()

        var test = sharedPreferences.getString("privateKey","def")
        Log.e("private Key", test)
    }


    @Throws(InvalidKeySpecException::class, NoSuchAlgorithmException::class)
    fun parseIntent(intent: Intent) {

        val pref = getSharedPreferences(PARTNER_FILE,Context.MODE_PRIVATE)
        val editor = pref.edit()
        val raw = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        val ndefMessage = raw[0] as NdefMessage
        val keyAsString: String
        val userName = String(ndefMessage.records[0].payload)
        val key = String(ndefMessage.records[0].payload)
        Log.e("Tag: ", String(ndefMessage.records[1].payload))
        Log.e("Without Tags: ", myCrytoUtil.parsePEMKeyAsStringToPublicKey(String(ndefMessage.records[1].payload)))
        editor.putString(userName,key)
        editor.apply()


    }

    override fun onNewIntent(intent: Intent) {
        //super.onNewIntent(intent);
        setIntent(intent)
    }


}

