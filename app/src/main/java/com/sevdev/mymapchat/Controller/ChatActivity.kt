package com.sevdev.mymapchat.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sevdev.mymapchat.Adapters.RecyclerAdapter
import com.sevdev.mymapchat.Model.Partner
import com.sevdev.mymapchat.R
import com.sevdev.mymapchat.Utility.*
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONArray
import java.net.URL

class ChatActivity : AppCompatActivity() {


    lateinit var  partners: ArrayList<Partner>
    lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        var thread = JsonThread()
        thread.start()
        var partner = Partner("Jim","69","69")
        var partnerFromActivity = intent.getParcelableExtra<Partner>(INTENT_TAG)

        adapter = RecyclerAdapter(partners,this)
        recycler_view.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
    }



    //ublic fun getPartnerList(): ArrayList<Partner>{
    //   var queue = RequestQueue(this)
    //   val apiResponse = object: StringRequest(GET_URL, Response.Listener {  }, Response.ErrorListener {  })}




    inner class JsonThread : Thread(){
        override fun run() {
            partners = getPartnerList()
        }
    }
}
