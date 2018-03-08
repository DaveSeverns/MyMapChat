package com.sevdev.mymapchat.Services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.sevdev.mymapchat.Model.Partner
import com.sevdev.mymapchat.Utility.GET_URL
import com.sevdev.mymapchat.Utility.LATITUDE
import com.sevdev.mymapchat.Utility.LONGITUDE
import com.sevdev.mymapchat.Utility.USER_NAME
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.net.URL

class PartnerService : Service() {

    lateinit var iBinder: IBinder

    override fun onBind(intent: Intent): IBinder? {

        iBinder = localBinder()
        return iBinder
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    class localBinder : Binder()

    fun getPartnerList(): ArrayList<Partner>{
        val apiResponse = URL(GET_URL)
        var partners = ArrayList<Partner>()
        val jsonArray : JSONArray = JSONArray(apiResponse)


        var i: Int= 0
        while(jsonArray.get(i) != null){
            var name = jsonArray.getJSONObject(i).getString(USER_NAME)
            var lat = jsonArray.getJSONObject(i).getString(LATITUDE)
            var lng = jsonArray.getJSONObject(i).getString(LONGITUDE)
            partners.add(Partner(name,lat,lng))
        }
        return partners
    }
}
