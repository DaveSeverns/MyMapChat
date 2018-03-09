package com.sevdev.mymapchat.Utility

import com.sevdev.mymapchat.Model.Partner
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by davidseverns on 3/8/18.
 */
interface KaMorrisClient {

    //Endpoint to make the the get request on
    @GET(GET_ENDPOINT)
    fun getPartnerList(): Call<ArrayList<Partner>>

    @POST(POST_ENDPOINT)
    fun addPartnerToList(@Body)
}