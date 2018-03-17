package com.sevdev.mymapchat.Services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.sevdev.mymapchat.Model.Partner
import com.sevdev.mymapchat.Utility.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL

class PartnerService : Service() {

    private var ioHelper: IOHelper? = null
    private var serviceThread: ServiceThread? = null

    private val mBinder = LocalBinder()


    override fun onBind(intent: Intent): IBinder? {
        ioHelper = IOHelper(applicationContext)
        serviceThread = ServiceThread()
        serviceThread!!.start()

        return mBinder
    }

    inner class LocalBinder : Binder() {
        internal val service: PartnerService
            get() = this@PartnerService
    }

    fun getStockInfo(symbol: String) {
        val t = object : Thread() {
            override fun run() {
                pullJSONFromUrl(symbol)
            }
        }
        t.start()
    }


    fun pullJSONFromUrl(username: String): Partner? {
        val stockJSONURL: URL
        var tempPartner: Partner? = null

        try {
            stockJSONURL = URL("http://dev.markitondemand.com/MODApis/Api/v2/Quote/json/?symbol=$symbol")
            val bufferedReader = BufferedReader(InputStreamReader(stockJSONURL.openStream()))
            var tempResponse: String?
            var response = ""

            tempResponse = bufferedReader.readLine()
            while (tempResponse != null) {
                response = response + tempResponse
                tempResponse = bufferedReader.readLine()
            }

            val partnerObject = JSONObject(response)
            tempPartner = Partner(partnerObject)


        } catch (e: Exception) {
            Log.e("Error", "Error grabbing partners")
            e.printStackTrace()
        }

        return tempPartner
    }

    private inner class ServiceThread : Thread() {
        override fun run() {
            super.run()
            while (true) {
                val threadMap = ioHelper!!.readFromFile()
                if (threadMap != null) {
                    for (entry in threadMap.entries) {
                        ioHelper!!.savePartnerToFile(pullJSONFromUrl(entry.key))
                        try {
                            Log.d("ServiceThread Ran", threadMap.toString())
                            sleep(60000)

                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                    }
                }

            }
        }
    }
}
