package com.sevdev.mymapchat.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.sevdev.mymapchat.Model.User
import com.sevdev.mymapchat.R
import kotlinx.android.synthetic.main.user_card_layout.view.*

/**
 * Created by davidseverns on 3/4/18.
 */
class RecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var collection : List<User>

    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        var userImage : ImageView
        var userText : TextView
        init {
            userImage = view.findViewById(R.id.user_picture)
            userText = view.findViewById(R.id.user_text)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent!!.context).inflate(viewType,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.bindViewHolder(holder, position)
    }
}