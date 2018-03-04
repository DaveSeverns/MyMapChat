package com.sevdev.mymapchat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sevdev.mymapchat.Model.User
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.properties.Delegates

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
