package com.valentin.musicplayer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.valentin.musicplayer.playback.Song
import com.valentin.musicplayer.services.MusicService

class MainViewModel: ViewModel(), Player.Listener {
    var isMusicServiceBound = false

    val song = MutableLiveData<Song>()

    private companion object {
        const val TAG = "MainViewModel"
    }
}