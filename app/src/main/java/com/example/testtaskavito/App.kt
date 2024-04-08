package com.example.testtaskavito

import android.app.Application
import com.example.testtaskavito.di.DaggerAppComponent

class App : Application() {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}