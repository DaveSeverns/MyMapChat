package com.sevdev.mymapchat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //initialize the Realm db
        enter_name_button.setOnClickListener{
            val intent : Intent = Intent(this,ChatActivity::class.java)
            startActivity(intent)
        }

    }

}
