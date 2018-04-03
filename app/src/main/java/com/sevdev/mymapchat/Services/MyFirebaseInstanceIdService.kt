package com.sevdev.mymapchat.Services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.sevdev.mymapchat.Utility.DEFAULT_USERNAME
import com.sevdev.mymapchat.Utility.NetworkManager
import retrofit2.Call
import retrofit2.Response

class MyFirebaseInstanceIdService: FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token
        Log.i("Token: ", "${token}")
        val sharedPref = getSharedPreferences("edu.temple.mapchat.USER",Context.MODE_PRIVATE)
        val userFromPref = sharedPref.getString("currentUser", DEFAULT_USERNAME)
        val kaMorrisClient = NetworkManager.networkCall()
        kaMorrisClient.addTokenToServer(userFromPref,token.toString())
                .enqueue(object: retrofit2.Callback<Void>{
                    override fun onFailure(call: Call<Void>?, t: Throwable?) {
                        Log.e("Call to Server ", "FAILED")
                    }

                    override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                        if (response?.code() == 200){
                            Log.i("Response Success Code:", " ${response.code()}")
                        }else{
                            Log.e("Response Error: ", "${response?.code()}")
                        }
                    }
                })

    }
}