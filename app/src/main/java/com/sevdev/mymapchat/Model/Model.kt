package com.sevdev.mymapchat.Model

import java.security.PublicKey

/**
 * Created by davidseverns on 3/4/18.
 */
object Model {
    data class User(var name : String,
                    var latitude : String,
                    var longitude : String,
                    var publicKey: PublicKey)
}