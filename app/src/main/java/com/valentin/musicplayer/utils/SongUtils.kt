package com.valentin.musicplayer.utils

import android.content.res.Resources
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.valentin.musicplayer.R
import com.valentin.musicplayer.activity.MainActivity
import com.valentin.musicplayer.playback.Song
import java.io.InputStream

object SongUtils {
    var songs: List<Song>? = null

    fun provideSongs(res: Resources): List<Song> {
        if (songs == null) {
            val in_s: InputStream = res.openRawResource(R.raw.songs)
            val b = ByteArray(in_s.available())
            in_s.read(b)
            val str = String(b)



            val moshi = Moshi.Builder().build()
            val listType = Types.newParameterizedType(List::class.java, Song::class.java)
            val adapter: JsonAdapter<List<Song>> = moshi.adapter(listType)

            songs = adapter.fromJson(str)!!
            Log.d(TAG, "Songs: $songs")
            return songs!!
        }
        else {
            return songs!!
        }
    }

    const val TAG = "SongUtils"
//    private fun readSongs(context: Context): List<Song> {
//
//    }
//
//    private fun getStringFromRaw(context: Context): String {
//        val resources = context.resources
//        val stream = resources.openRawResource(R.raw.songs)
//        val result = stream.
//    }
}