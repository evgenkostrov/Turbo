package com.example.turbo

import android.app.Application
import com.example.turbo.model.AkbsService

class App:Application() {

    val akbsService= AkbsService()
}