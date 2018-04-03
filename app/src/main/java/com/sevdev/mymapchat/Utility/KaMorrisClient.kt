package com.sevdev.mymapchat.Utility

import com.sevdev.mymapchat.Model.Partner
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by davidseverns on 3/8/18.
 */
interface KaMorrisClient {

    //Endpoint to make the the get request on
    @GET(GET_ENDPOINT)
    fun getPartnerList(): Observable<ArrayList<Partner>>

    @POST(POST_ENDPOINT)
    @FormUrlEncoded
    fun addPartnerToList(@Field("user") userName: String?,
                         @Field("latitude") latitude: String?,
                         @Field("longitude") longitude:String?) : Call<Partner>

    @POST(TOKEN_POST)
    @FormUrlEncoded
    fun addTokenToServer(
            @Field("user") user: String,
            @Field("token") token: String) : Call<Void>
}