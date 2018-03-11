package com.sevdev.mymapchat.Controller

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.RemoteCallbackList
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
import com.sevdev.mymapchat.Utility.*
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class ChatActivity : AppCompatActivity(), PartnerListFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private lateinit var partnerListFragment: PartnerListFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        partnerListFragment = PartnerListFragment()

        val fragTransaction = fragmentManager.beginTransaction()
        fragTransaction.replace(R.id.single_pane_frame, partnerListFragment).commit()


    }




}
