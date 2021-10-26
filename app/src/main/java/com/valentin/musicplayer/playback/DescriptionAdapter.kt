package com.valentin.musicplayer.playback

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.valentin.musicplayer.utils.SongUtils

class DescriptionAdapter(private val context: Context): PlayerNotificationManager.MediaDescriptionAdapter {
    // caching Images
    private val map = HashMap<String, Bitmap>()


    override fun getCurrentContentTitle(player: Player): CharSequence {
        val position = player.currentWindowIndex
        // TODO: fix SongUtils
        return SongUtils.songs!![position].artist
    }

    override fun createCurrentContentIntent(player: Player): PendingIntent? {
        // TODO: intent to open activity
        return null
    }

    override fun getCurrentContentText(player: Player): CharSequence {
        val position = player.currentWindowIndex
        return SongUtils.songs!![position].name
    }

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        val position = player.currentWindowIndex
        val song = SongUtils.songs!![position]

        val bitmap = map[song.imageUrl]
        // TODO: if image already cached should return it
        // Do not know how to load cached bitmap on UI thread using Glide
//        val bmp = Glide.with(context)
//            .asBitmap()
//            .onlyRetrieveFromCache(true)
//            .load(song.imageUrl)
//            .get()

        if (bitmap == null) {
            Glide.with(context)
                .asBitmap()
                .load(song.imageUrl)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        map[song.imageUrl] = resource
                        callback.onBitmap(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
        return bitmap
    }

    private companion object {
        const val TAG = "DescriptionAdapter"
    }
}