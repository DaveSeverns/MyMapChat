package com.sevdev.mymapchat.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.security.PublicKey

/**
 * Created by davidseverns on 2/28/18.
 * This is the data model for a user of the MapChat,
 * should be able to keep track of Local User as well as other "partners"
 * The model will have fields for each users location(latlng) stored as string, Their Name (from user server)
 * and their public key if they have been in contact with primary user
 */
@RealmClass
open class User() : RealmObject(){
    @PrimaryKey
    var userName : String = ""
    var latitude : Double? = null
    var longitude : Double? = null
    var publicKey : String? = null

}