package com.sevdev.mymapchat.Utility

import android.content.Context
import android.widget.Toast

import java.io.IOException
import java.io.StringWriter
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import org.spongycastle.openssl.jcajce.JcaPEMWriter


import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException

/**
 * Created by davidseverns on 2/22/18.
 */

class MyCrytoUtil(internal var context: Context) {
    private lateinit var cipher: Cipher
    private lateinit var cipher2: Cipher
    /*
    uses the cipher object to encode the plain text from string to byte array then encrypt it with public key
     */
    @Throws(NoSuchAlgorithmException::class, NoSuchPaddingException::class, InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun encryptText(key: PrivateKey, plainText: ByteArray): ByteArray {
        cipher2 = Cipher.getInstance("RSA")
        cipher2.init(Cipher.ENCRYPT_MODE, key)
        Toast.makeText(context, "Text Encrypted", Toast.LENGTH_SHORT).show()
        return cipher2.doFinal(plainText)
    }

    /*
    opposite of the encrypt method, cipher object uses the private key matched to the public key to decode the message to
    byte array and convert it back to a string to put it plain text
     */
    @Throws(InvalidKeyException::class, NoSuchPaddingException::class, NoSuchAlgorithmException::class, BadPaddingException::class, IllegalBlockSizeException::class, UnsupportedEncodingException::class)
    fun decryptText(key: PublicKey, encryptedText: ByteArray): String {
        cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, key)
        Toast.makeText(context, "Text Decrypted", Toast.LENGTH_SHORT).show()
        return byteArrayToString(cipher.doFinal(encryptedText))

    }

    //function to turn string to byte array
    fun stringToByteArray(string: String): ByteArray {
        return string.toByteArray()
    }

    //get string back from byte array
    @Throws(UnsupportedEncodingException::class)
    fun byteArrayToString(array: ByteArray): String {
        return String(array)
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPubKeyFromString(keyAsString: String): PublicKey {
        //found reference for this code, the purpose is to take the key as string and convert to byte array
        // and then use the key factory and key spec to convert back to public key
        val publicBytes = android.util.Base64.decode(keyAsString, android.util.Base64.DEFAULT)
        val keySpec = X509EncodedKeySpec(publicBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPrivKeyFromString(keyAsString: String): PrivateKey {
        val publicBytes = android.util.Base64.decode(keyAsString, android.util.Base64.DEFAULT)
        val keySpec = PKCS8EncodedKeySpec(publicBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec)
    }

    @Throws(IOException::class)
    fun publicKeyToPEMFile(pk: PublicKey): String {
        val writer = StringWriter()
        val pemWriter = JcaPEMWriter(writer)
        pemWriter.writeObject(pk)
        pemWriter.close()
        return writer.toString()
    }

    /**
     * This method takes the public key in the .pem format,
     * removes the header and footer and returns it back as Public Key
     * @param pemString
     * @return
     */
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun parsePEMKeyAsStringToPublicKey(pemString: String): String {
        var tempKeyString = pemString.replace("-----BEGIN PUBLIC KEY-----\n", "")
        tempKeyString = tempKeyString.replace("-----END PUBLIC KEY-----\n", "")

        return tempKeyString
    }

}