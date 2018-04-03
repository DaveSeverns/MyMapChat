package com.sevdev.mymapchat.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sevdev.mymapchat.R

class MessengerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        val partnerToChatWith = intent.getStringExtra("username")
        Toast.makeText(this, partnerToChatWith,Toast.LENGTH_LONG).show()
    }


}
