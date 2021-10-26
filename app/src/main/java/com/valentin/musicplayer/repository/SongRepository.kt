package com.valentin.musicplayer.repository

import android.content.Context
import com.valentin.musicplayer.playback.Song
import com.valentin.musicplayer.utils.SongUtils

class SongRepository(context: Context) {
    val songs: List<Song> = if (SongUtils.songs == null)
        SongUtils.provideSongs(context.resources)
    else
        SongUtils.songs!!

}