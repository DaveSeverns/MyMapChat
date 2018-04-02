package com.sevdev.mymapchat.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by davidseverns on 3/7/18.
 */
class Partner(val username:String? = "", val latitude :Double?= null,val longitude:Double? = null, var distanceTo: Float): Parcelable,
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

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readFloat())



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeDouble(latitude!!)
        parcel.writeDouble(longitude!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Partner> {
        override fun createFromParcel(parcel: Parcel): Partner {
            return Partner(parcel)
        }

        override fun newArray(size: Int): Array<Partner?> {
            return arrayOfNulls(size)
        }
    }
}