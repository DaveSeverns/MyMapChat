package com.sevdev.mymapchat.Utility

import android.Manifest
import android.app.Activity
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.google.gson.GsonBuilder
import com.sevdev.mymapchat.Adapters.RecyclerAdapter
import com.sevdev.mymapchat.Model.Partner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by davidseverns on 3/8/18.
 */
class NetworkManager() {

    companion object {
        fun networkCall(): KaMorrisClient{
            val gson = GsonBuilder().create()
            val builder = Retrofit.Builder().baseUrl(BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson))
            val retrofit = builder.build()
            val client = retrofit.create(KaMorrisClient::class.java)
            return client
        }
    }




    fun postPartnerToServer(partner: Partner){
        val call = networkCall().addPartnerToList(partner.username,partner.toString(),partner.longitude.toString())
        call.enqueue(object : Callback<Partner>{
            override fun onFailure(call: Call<Partner>?, t: Throwable?) {
                Log.e(ERROR_HERE_TAG, t.toString())
            }

            override fun onResponse(call: Call<Partner>?, response: Response<Partner>?) {
                Log.d(ERROR_HERE_TAG, "No error ${response!!.body().toString()}")
            }
        })
    }

    fun checkPermission(activity: Activity){
        ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),10)
    }
}