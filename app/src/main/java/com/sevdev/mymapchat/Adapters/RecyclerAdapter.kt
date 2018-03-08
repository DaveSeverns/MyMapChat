package com.sevdev.mymapchat.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sevdev.mymapchat.Model.Partner
import com.sevdev.mymapchat.R

/**
 * Created by davidseverns on 3/4/18.
 */
class RecyclerAdapter(val partners : ArrayList<Partner>, val context: Context) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.user_card_layout,parent,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return partners.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder?.bindPartner(partners[position],context)
    }


    inner class MyViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView){
        val userImage = itemView?.findViewById<ImageView>(R.id.user_picture)
        val userText = itemView?.findViewById<TextView>(R.id.user_text)

        fun bindPartner(partner : Partner, context : Context){
            userText?.text = partner.name
        }

    }


}