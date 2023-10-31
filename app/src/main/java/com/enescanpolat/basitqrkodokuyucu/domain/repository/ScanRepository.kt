package com.enescanpolat.basitqrkodokuyucu.domain.repository

import kotlinx.coroutines.flow.Flow

interface ScanRepository {


    fun startScaning(): Flow<String?>


}