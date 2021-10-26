package com.valentin.musicplayer.di

import android.content.Context
import com.valentin.musicplayer.repository.SongRepository
import com.valentin.musicplayer.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    fun provideRepository(context: Context): SongRepository {
        return SongRepository(context)
    }

    @Singleton
    @Provides
    fun provideMainViewModelFactory(repository: SongRepository): MainViewModelFactory {
        return MainViewModelFactory(repository)
    }
}
