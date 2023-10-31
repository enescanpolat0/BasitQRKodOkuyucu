package com.enescanpolat.basitqrkodokuyucu.data.dependencyinjection

import android.app.Application
import android.content.Context
import com.enescanpolat.basitqrkodokuyucu.data.repository.ScanRepositoryImpl
import com.enescanpolat.basitqrkodokuyucu.domain.repository.ScanRepository
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object appModule {


    @Singleton
    @Provides
    fun injectContext(application:Application):Context{
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun injectOptions():GmsBarcodeScannerOptions{
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    }

    @Singleton
    @Provides
    fun injectScanner(context: Context,options: GmsBarcodeScannerOptions):GmsBarcodeScanner{

        return GmsBarcodeScanning.getClient(context, options)


    }

    @Singleton
    @Provides
    fun injectScanRepository(context: Context,scanner: GmsBarcodeScanner):ScanRepository{
        return ScanRepositoryImpl(context,scanner)
    }

}