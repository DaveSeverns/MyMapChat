package com.sevdev.mymapchat.Utility

import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.sevdev.mymapchat.Adapters.RecyclerAdapter
import com.sevdev.mymapchat.Model.Partner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by davidseverns on 3/8/18.
 */
class NetworkManager(context: Context) {
    fun networkCall(): KaMorrisClient{
        val gson = GsonBuilder().create()
        val builder = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
        val retrofit = builder.build()
        val client = retrofit.create(KaMorrisClient::class.java)
        return client
    }

    fun getPartnerList(adapter: RecyclerAdapter): ArrayList<Partner>{
        val call = networkCall().getPartnerList()
        val partners = ArrayList<Partner>()
        call.enqueue(object : Callback<ArrayList<Partner>> {
            override fun onResponse(call: Call<ArrayList<Partner>>?, response: retrofit2.Response<ArrayList<Partner>>?) {
                partners.addAll( response!!.body()!!)
                adapter.notifyDataSetChanged()
                println(partners?.get(0)?.username)
            }

            override fun onFailure(call: Call<ArrayList<Partner>>?, t: Throwable?) {
                Log.e(ERROR_HERE_TAG, "onFailure")
            }
        })

        return partners
    }
}