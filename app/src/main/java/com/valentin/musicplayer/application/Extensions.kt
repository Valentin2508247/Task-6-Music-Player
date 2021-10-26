package com.valentin.musicplayer.application

import android.content.Context
import com.valentin.musicplayer.di.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApplication -> appComponent
        else -> this.applicationContext.appComponent
    }
