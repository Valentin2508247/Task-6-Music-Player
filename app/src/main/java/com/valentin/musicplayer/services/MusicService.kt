package com.valentin.musicplayer.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.valentin.musicplayer.playback.DescriptionAdapter
import com.valentin.musicplayer.playback.MusicState
import com.valentin.musicplayer.playback.Song
import com.valentin.musicplayer.utils.NotificationUtils
import com.valentin.musicplayer.utils.SongUtils


class MusicService : Service(), Player.Listener {
    // TODO: background execution limits?
    private var musicState = MusicState.STOP
    var exoPlayer: ExoPlayer? = null

    private var mediaSession: MediaSessionCompat? = null
    private var mediaSessionConnector: MediaSessionConnector? = null

    private var playerNotificationManager: PlayerNotificationManager? = null
    private val adapter: DescriptionAdapter by lazy {
        DescriptionAdapter(this)
    }

    private val binder by lazy { MusicBinder() }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service onCreate")
        NotificationUtils.createNotificationChannel(this)
        initializePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "On Destroy")
        stopMusic()
    }

    fun runAction(state: MusicState) {
        musicState = state
        Log.d(TAG, "State: ${state.name}")
        when (state) {
            MusicState.PlAY -> {
                startMusic()
            }
            MusicState.PAUSE -> {
                pauseMusic()
            }
            MusicState.STOP -> {
                stopMusic()
            }
            MusicState.PLAY_NEXT -> {
                playNext()
            }
            MusicState.PLAY_PREVIOUS -> {
                playPrevious()
            }
        }
    }

    private fun startMusic() {
        Log.d(TAG, "Service startMusic()")
        exoPlayer?.play()
        exoPlayer?.playWhenReady = true
    }

    private fun pauseMusic() {
        Log.d(TAG, "Service pauseMusic()")
        exoPlayer?.playWhenReady = false
    }

    private fun playNext() {
        Log.d(TAG, "Service playNext()")
        exoPlayer?.seekToNextWindow()
    }

    private fun playPrevious() {
        Log.d(TAG, "Service playPrevious()")
        exoPlayer?.seekToPreviousWindow()
    }

    private fun stopMusic() {
        playerNotificationManager?.setPlayer(null);

        exoPlayer?.release()
        exoPlayer = null
        if (mediaSession != null) {
            mediaSession?.release()
        }
    }

    private fun initializePlayer() {
        exoPlayer = SimpleExoPlayer.Builder(this).build()
        exoPlayer?.addListener(this)

        // Audio focus
        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .build()
        (exoPlayer as SimpleExoPlayer).setAudioAttributes(audioAttributes, true);

        val songs = SongUtils.provideSongs(resources).map {
            makeMediaItem(it)
        }
        exoPlayer?.addMediaItems(songs)
        exoPlayer?.prepare()

        // Notifications
        playerNotificationManager = PlayerNotificationManager.Builder(
            this,
            NotificationUtils.NOTIFICATION_ID,
            NotificationUtils.CHANNEL_ID
        )
            .setMediaDescriptionAdapter(adapter)
            .build()
        // customize notification controls
        playerNotificationManager!!.setUseNextActionInCompactView(true)
        playerNotificationManager!!.setUsePreviousActionInCompactView(true)
        playerNotificationManager!!.setPlayer(exoPlayer)

        // Not sure what it is used for
        // Media session
        mediaSession = MediaSessionCompat(this, "sample")
        mediaSessionConnector = MediaSessionConnector(mediaSession!!)
        mediaSessionConnector!!.setPlayer(exoPlayer)
        mediaSession?.isActive = true
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        Log.d(TAG, "onMediaItemTransition: position ${exoPlayer?.currentWindowIndex}")
    }

    private fun makeMediaItem(song: Song): MediaItem {
        return MediaItem.fromUri(song.songUrl)
    }

    private companion object {
        const val TAG = "MusicService"
    }
}
