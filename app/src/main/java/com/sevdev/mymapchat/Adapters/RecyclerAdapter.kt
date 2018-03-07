package com.sevdev.mymapchat.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sevdev.mymapchat.Model.Model
import com.sevdev.mymapchat.R

/**
 * Created by davidseverns on 3/4/18.
 */
class RecyclerAdapter(private var users : ArrayList<Model.User>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(viewType,parent,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = users[position]
        holder.userText.text = currentUser.name
    }


    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        var userImage : ImageView
        var userText : TextView
        init {
            userImage = itemView.findViewById(R.id.user_picture)
            userText = itemView.findViewById(R.id.user_text)
        }

    }


}