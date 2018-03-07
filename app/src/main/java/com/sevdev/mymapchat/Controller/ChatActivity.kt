package com.sevdev.mymapchat.Controller

import android.app.FragmentManager
import android.graphics.ColorSpace
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sevdev.mymapchat.Adapters.RecyclerAdapter
import com.sevdev.mymapchat.Model.Model
import com.sevdev.mymapchat.R
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {


    private var layoutManager : RecyclerView.LayoutManager?= null
    private var adapter : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>? = null
    private var users = null as ArrayList<Model.User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        var user1 = Model.User("","","","")
        users?.add(user1)
        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        adapter = RecyclerAdapter(users)
        recycler_view.adapter = adapter
    }
}
