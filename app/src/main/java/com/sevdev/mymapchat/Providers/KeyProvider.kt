package com.sevdev.mymapchat.Providers

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Base64

import java.security.Key
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.PublicKey


class KeyProvider : ContentProvider() {

    private lateinit var keyPair: KeyPair
    private lateinit var kpg: KeyPairGenerator
    private lateinit var publicKey: PublicKey
    private lateinit var privateKey: PrivateKey

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        // Implement this to handle requests to delete one or more rows.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        // at the given URI.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        //
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate(): Boolean {
        //
        return false
    }

    //provides a private and public key to the client in the form of a string will have to be converted back to keys
    //when encrypting and decrypting
    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        generateKeys()
        publicKey = keyPair.public
        privateKey = keyPair.private
        //encode the keys from byte array to string
        val publicKeyAsString = Base64.encodeToString(publicKey.encoded, Base64.DEFAULT)
        val privateKeyAsString = Base64.encodeToString(privateKey.encoded, Base64.DEFAULT)
        val headings = arrayOf("public key", "private key")
        val matrixCursor = MatrixCursor(headings)
        // add the public and private keys as strings and package them as row in the cursor
        matrixCursor.addRow(arrayOf(publicKeyAsString, privateKeyAsString))

        return matrixCursor
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        //
        throw UnsupportedOperationException("Not yet implemented")
    }

    //using keypairgenerator to get public and private RSA keys, stores them in key pair object
    fun generateKeys(): Boolean {
        var encrypted = false
        try {
            kpg = KeyPairGenerator.getInstance("RSA")
            kpg.initialize(1024)
            keyPair = kpg.generateKeyPair()
            encrypted = true

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return encrypted
    }
}
