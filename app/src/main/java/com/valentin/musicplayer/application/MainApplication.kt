package com.valentin.musicplayer.application

import android.app.Application
import com.valentin.musicplayer.di.AppComponent
import com.valentin.musicplayer.di.DaggerAppComponent

class MainApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}
