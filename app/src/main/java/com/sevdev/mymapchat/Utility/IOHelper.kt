package com.sevdev.mymapchat.Utility

import android.content.Context
import android.util.Log
import com.sevdev.mymapchat.Model.Partner
import java.io.*
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by davidseverns on 3/16/18.
 */
class IOHelper(var context: Context) {


    val listOfPartners: ArrayList<Partner>
        get() {
            var partnerList = ArrayList<Partner>()
            val mapToParse = readFromFile()

            for (partner in mapToParse) {
                partnerList.add(partner.value)
            }
            return partnerList
        }

    fun readFromFile(): HashMap<String, Partner> {
        var partnerMap = HashMap<String, Partner>()

        try {
            val fileInputStream = context.openFileInput(PARTNER_FILE)
            val objectInputStream = ObjectInputStream(fileInputStream)
            partnerMap = objectInputStream.readObject() as HashMap<String, Partner>
            Log.d("From file in activity", partnerMap.toString())

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return partnerMap
    }

    fun savePartnersToFile(partners: ArrayList<Partner>): Boolean {
        var saved = false
        //TODO if this fails add the load back in.
        val tempMap = readFromFile()
        for(partner in partners){
            tempMap[partner.username.toString()] = partner
        }
        val fos: FileOutputStream
        try {
            fos = context.openFileOutput(PARTNER_FILE, Context.MODE_PRIVATE)
            val path = context.filesDir.absolutePath
            Log.d("Saving to path", path)
            var oos: ObjectOutputStream? = null
            oos = ObjectOutputStream(fos)
            oos.writeObject(tempMap)
            oos.close()
            fos.close()
            saved = true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return saved
    }

    fun getPartnerByUserName(username: String): Partner?{
        var tempPartner = Partner() as Partner?
        val partnerMap = readFromFile()
        if (partnerMap.isNotEmpty()){
            tempPartner = partnerMap.get(username.toString())
        }

        return tempPartner
    }
}