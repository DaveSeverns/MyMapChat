package com.sevdev.mymapchat.Controller

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

class ChatActivity : AppCompatActivity() {


    lateinit var  defaultPartners: ArrayList<Partner>
    lateinit var adapter: RecyclerAdapter
    lateinit var partners : ArrayList<Partner>
    lateinit var networkManager : NetworkManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        networkManager = NetworkManager(this)
        val layoutManager = LinearLayoutManager(this)
        var partner = Partner("Jim","69","69")
        var partnerFromActivity = intent.getParcelableExtra<Partner>(INTENT_TAG)
        partners = ArrayList()
        adapter = RecyclerAdapter(partners,this)
        defaultPartners = ArrayList()
        defaultPartners.add(partner)
        defaultPartners.add(partnerFromActivity)

        val tempList = networkManager.getPartnerListNetwork(adapter)
        partners.addAll(tempList)

        //val gson = GsonBuilder().create()
        //val builder = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
        //val retrofit = builder.build()
        //val client = retrofit.create(KaMorrisClient::class.java)
        //val call = client.getPartnerList()
        //Log.e(ERROR_HERE_TAG, call.toString())
        //call.enqueue(object : Callback<ArrayList<Partner>>{
        //    override fun onResponse(call: Call<ArrayList<Partner>>?, response: retrofit2.Response<ArrayList<Partner>>?) {
        //        Log.e(ERROR_HERE_TAG,"onResponse")
        //        partners.addAll( response!!.body()!!)
        //        adapter.notifyDataSetChanged()
        //        println(partners?.get(0)?.username)
        //    }
//
        //    override fun onFailure(call: Call<ArrayList<Partner>>?, t: Throwable?) {
        //        Log.e(ERROR_HERE_TAG, "onFailure")
        //    }
        //})

        if(0 != null){
            adapter = RecyclerAdapter(partners,this)

        }
        else{
            adapter = RecyclerAdapter(defaultPartners, this)
        }

        recycler_view.adapter = adapter

        recycler_view.layoutManager = layoutManager
    }



    //ublic fun getPartnerList(): ArrayList<Partner>{
    //   var queue = RequestQueue(this)
    //   val apiResponse = object: StringRequest(GET_URL, Response.Listener {  }, Response.ErrorListener {  })}




   //inner class JsonThread : Thread(){
   //    override fun run() {
   //        partners = getPartnerList()
   //    }
   //}
}
