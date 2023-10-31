package com.enescanpolat.basitqrkodokuyucu.data.repository

import android.content.Context
import android.widget.Toast
import com.enescanpolat.basitqrkodokuyucu.domain.repository.ScanRepository
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScanRepositoryImpl @Inject  constructor(
    private val context:Context,
    private val scanner:GmsBarcodeScanner
): ScanRepository {


    override fun startScaning(): Flow<String?> {

        return callbackFlow {

            scanner.startScan()
                .addOnSuccessListener { barcode->

                    launch {

                        send(getDetails(barcode))
                    }


                }
                .addOnFailureListener { Toast.makeText(context,"${it.localizedMessage?:"Error"}",Toast.LENGTH_LONG).show() }


            awaitClose {  }


        }

    }

    private fun getDetails(barcode:Barcode):String{

        return when(barcode.valueType){

            Barcode.TYPE_WIFI ->{
                val ssid = barcode.wifi!!.ssid
                val password = barcode.wifi!!.password
                val type = barcode.wifi!!.encryptionType
                "SSID:${ssid},PASSWORD:${password},TYPE:${type}"
            }

            Barcode.TYPE_URL->{
                "URL:${barcode.url!!.url}"
            }

            Barcode.TYPE_PRODUCT->{
                "PRODUCTTYPE:${barcode.displayValue}"
            }

            Barcode.TYPE_EMAIL->{
                "EMAIL:${barcode.email}"
            }

            Barcode.TYPE_CONTACT_INFO->{
                "CONTACT:${barcode.contactInfo}"
            }

            Barcode.TYPE_PHONE->{
                "PHONE:${barcode.phone}"
            }

            Barcode.TYPE_CALENDAR_EVENT->{
                "CALENDAR EVENT:${barcode.calendarEvent}"
            }

            Barcode.TYPE_GEO->{
                "GEO POINT:${barcode.geoPoint}"
            }

            Barcode.TYPE_ISBN->{
                "ISBN:${barcode.displayValue}"
            }

            Barcode.TYPE_DRIVER_LICENSE->{
                "DRIVING LISENCE:${barcode.driverLicense}"
            }

            Barcode.TYPE_SMS->{
                "SMS:${barcode.sms}"
            }

            Barcode.TYPE_TEXT->{
                "TEXT:${barcode.rawValue}"
            }

            Barcode.TYPE_UNKNOWN->{
                "UNKNOWN:${barcode.rawValue}"
            }

            else->{
                barcode.rawValue?:"Couldn't determine"
            }

        }

    }
}