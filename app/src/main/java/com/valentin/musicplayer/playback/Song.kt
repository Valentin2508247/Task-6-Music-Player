package com.valentin.musicplayer.playback

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Song(
    @Json(name = "title") val name: String,
    @Json(name = "artist") val artist: String,
    @Json(name = "trackUri") val songUrl: String,
    @Json(name = "bitmapUri") val imageUrl: String,
    @Json(name = "duration") val duration: Long
)