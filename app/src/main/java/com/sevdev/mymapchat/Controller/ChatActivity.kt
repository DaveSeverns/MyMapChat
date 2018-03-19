package com.sevdev.mymapchat.Controller

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteCallbackList
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sevdev.mymapchat.Adapters.RecyclerAdapter
import com.sevdev.mymapchat.Model.Partner
import com.sevdev.mymapchat.R
import com.sevdev.mymapchat.Services.PartnerService
import com.sevdev.mymapchat.Utility.*
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class ChatActivity : AppCompatActivity(), PartnerListFragment.OnParnterListFragmentInteractionListener, PartnerMapFragment.OnMapFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private lateinit var partnerListFragment: PartnerListFragment
    private lateinit var mapFragment : PartnerMapFragment
    private lateinit var mService: PartnerService
    private var mBound2 = false
    private lateinit var ioHelper: IOHelper

    private val mConnection = object :ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as PartnerService.LocalBinder
            mService = binder.service
            mBound2 = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBound2 = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        ioHelper = IOHelper(applicationContext)

        partnerListFragment = PartnerListFragment()
        mapFragment = PartnerMapFragment()

        val fragTransaction = fragmentManager.beginTransaction()
        fragTransaction.replace(R.id.single_pane_frame, partnerListFragment).commit()

        UpdateListTask().execute()


    }

    override fun itemClicked(partner: String?) {
        val mapTransaction = fragmentManager.beginTransaction()
        mapTransaction.replace(R.id.single_pane_frame, mapFragment).addToBackStack(null).commit()
        fragmentManager.executePendingTransactions()
    }


    override fun onStart() {
        super.onStart()
        val serviceIntent = Intent(this, PartnerService::class.java)
        bindService(serviceIntent,mConnection, Context.BIND_AUTO_CREATE)
        Log.e("Running Service", "onStart")
    }

    override fun onStop() {
        super.onStop()
        unbindService(mConnection)
        mBound2 = false
    }

    private inner class UpdateListTask : AsyncTask<ArrayList<Partner>, Void, ArrayList<Partner>>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(partners: ArrayList<Partner>) {
            super.onPostExecute(partners)
            /*if(stock.equalsIgnoreCase("Success")){
               portfolioFragment.parsePortfolioMap(ioHelper.readFromFile());
           }*/
            if (partners.isEmpty()) {
                Toast.makeText(this@ChatActivity, "error", Toast.LENGTH_LONG).show()
            } else {
                partnerListFragment.addPartnersToList(partners)
            }


        }

        override fun onProgressUpdate(vararg values: Void) {
            super.onProgressUpdate(*values)
        }

        override fun onCancelled(aVoid: ArrayList<Partner>) {
            super.onCancelled(aVoid)
        }

        override fun onCancelled() {
            super.onCancelled()
        }

        override fun doInBackground(vararg partners: ArrayList<Partner>): ArrayList<Partner> {
            val bgPartners = partners as ArrayList<Partner>
            val partnersFromService = mService.pullJSONPartnersFromUrl()
            if (partnersFromService != null) {
                ioHelper.savePartnersToFile(partnersFromService)
                return partnersFromService
            } else {
                return bgPartners
            }

        }
    }


}

