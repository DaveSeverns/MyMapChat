package com.sevdev.mymapchat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sevdev.mymapchat.Model.User
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlin.properties.Delegates

class RegisterActivity : AppCompatActivity() {

    var realm : Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //initialize the Realm db
        Realm.init(this)
        val config = RealmConfiguration.Builder().name(getString(R.string.realm_name)).build()
        realm = Realm.getInstance(config)

        realm!!.beginTransaction()
        val user = realm!!.createObject(User:: class.java, "Dave")

        user.latitude = 39.999995
        user.longitude = -75.122343
        user.publicKey = "1234k"

        realm!!.commitTransaction()

        //realm = Realm.getInstance(config)
        //Log.e("Realm Path",realm.path)
    }

    override fun onStop() {
        super.onStop()
        realm!!.close()
    }
}
