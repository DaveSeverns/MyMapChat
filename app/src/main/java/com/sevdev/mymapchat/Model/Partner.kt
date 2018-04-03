package com.sevdev.mymapchat.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by davidseverns on 3/7/18.
 */
class Partner(val username:String? = "", val latitude :String?= null,val longitude:String? = null, var distanceTo: Float):
        Serializable,
        Comparable<Partner> {

    //lambda function to return an int when the object calling it is less, greater or equal to other
    override fun compareTo(other: Partner): Int = when {
        //if distance is less
        distanceTo < other.distanceTo -> -1
        //if distance is greater
        distanceTo > other.distanceTo -> 1
        //if they are equal
        else -> 0
    }
}