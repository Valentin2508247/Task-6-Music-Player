package com.valentin.musicplayer.di

import android.content.Context
import com.valentin.musicplayer.activity.MainActivity
import com.valentin.musicplayer.fragments.SongFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

    // activity
    fun inject(activity: MainActivity)

    // fragments
    fun inject(fragment: SongFragment)
}
