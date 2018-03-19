package com.sevdev.mymapchat.Services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.sevdev.mymapchat.Adapters.RecyclerAdapter
import com.sevdev.mymapchat.Model.Partner
import com.sevdev.mymapchat.Utility.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
                pullJSONPartnersFromUrl()
            }
        }
        t.start()
    }


    fun pullJSONPartnersFromUrl(): ArrayList<Partner> {
            val call = NetworkManager.networkCall().getPartnerList()
            var partners = ArrayList<Partner>()
            call.enqueue(object : Callback<ArrayList<Partner>> {
                override fun onResponse(call: Call<ArrayList<Partner>>?, response: Response<ArrayList<Partner>>?) {
                    partners.addAll( response!!.body()!!)
                    println(partners?.get(0)?.username)
                }

                override fun onFailure(call: Call<ArrayList<Partner>>?, t: Throwable?) {
                    Log.e(ERROR_HERE_TAG, "onFailure")
                }
            })
            return partners
        }


    private inner class ServiceThread : Thread() {
        override fun run() {
            super.run()
            while (true) {
                val threadMap = ioHelper!!.readFromFile()
                if (threadMap != null) {
                    for (entry in threadMap.entries) {
                        ioHelper!!.savePartnersToFile(pullJSONPartnersFromUrl())
                        try {
                            Log.d("ServiceThread Ran", threadMap.toString())
                            sleep(30000)

                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                    }
                }

            }
        }
    }
}
