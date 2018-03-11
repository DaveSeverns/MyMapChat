package com.sevdev.mymapchat.Utility

import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.sevdev.mymapchat.Adapters.RecyclerAdapter
import com.sevdev.mymapchat.Model.Partner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by davidseverns on 3/8/18.
 */
class NetworkManager(context: Context) {

    companion object {
        fun networkCall(): KaMorrisClient{
            val gson = GsonBuilder().create()
            val builder = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
            val retrofit = builder.build()
            val client = retrofit.create(KaMorrisClient::class.java)
            return client
        }
    }




    fun postPartnerToServer(partner: Partner){
        val call = networkCall().addPartnerToList(partner.username,partner.latitude,partner.longitude)
        call.enqueue(object : Callback<Partner>{
            override fun onFailure(call: Call<Partner>?, t: Throwable?) {
                Log.e(ERROR_HERE_TAG, t.toString())
            }

            override fun onResponse(call: Call<Partner>?, response: Response<Partner>?) {
                Log.d(ERROR_HERE_TAG, "No error ${response!!.body().toString()}")
            }
        })
    }
}