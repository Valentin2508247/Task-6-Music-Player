package com.valentin.musicplayer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.valentin.musicplayer.activity.MainActivity
import com.valentin.musicplayer.playback.Song
import com.valentin.musicplayer.repository.SongRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: SongRepository): ViewModel(){
    var isMusicServiceBound = false

    private val songs = repository.songs
    val currentSong = MutableLiveData<Song>()
    val currentTime = MutableLiveData<Long>()

    fun mediaTransition(position: Int?) {
        position?.let {
            if (position >= 0 && position < songs.size)
                currentSong.value = songs[position]
        }
    }

    fun playbackTime(time: Long) {
        currentTime.value = time
    }

    private companion object {
        const val TAG = "MainViewModel"
    }
}

class MainViewModelFactory @Inject constructor(private val repository: SongRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
