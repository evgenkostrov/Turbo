package com.example.turbo.data.remote

import com.example.turbo.data.entities.Battery
import com.example.turbo.util.Constants.BATTERY_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class BatteryDatabase {

    private val firestore = FirebaseFirestore.getInstance()
    private val batteryCollection = firestore.collection(BATTERY_COLLECTION)

    suspend fun getAllBattery(): List<Battery> {
        return try {
            batteryCollection.get().await().toObjects(Battery::class.java)
        } catch(e: Exception) {
            emptyList()
        }
    }
}