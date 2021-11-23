package com.example.turbo

import android.app.Application
import com.example.turbo.model.AkbsService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application() {

    val akbsService= AkbsService()
}