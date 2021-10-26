package com.valentin.musicplayer.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Resources
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.valentin.musicplayer.R
import com.valentin.musicplayer.databinding.ActivityMainBinding
import com.valentin.musicplayer.fragments.SongFragment
import com.valentin.musicplayer.playback.MusicState
import com.valentin.musicplayer.playback.Song
import com.valentin.musicplayer.services.MusicService
import com.valentin.musicplayer.viewmodel.MainViewModel
import java.io.InputStream
import java.lang.reflect.Type


class MainActivity : AppCompatActivity(), SongFragment.SongFragmentListener {

    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var musicService: MusicService? = null


    private val boundServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder: MusicService.MusicBinder = service as MusicService.MusicBinder
            musicService = binder.getService()
            viewModel.isMusicServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            musicService?.runAction(MusicState.STOP)
            musicService = null
            viewModel.isMusicServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: display playback info
        // TODO: provide playback controls
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        if (!viewModel.isMusicServiceBound) {
            bindToMusicService()
        }
    }

    override fun onDestroy() {
        // TODO: fix playback on screen rotation
        // After super.onDestroy() not called ????
        Log.d(TAG, "OnDestroy")
        unBindMusicService()
        super.onDestroy()
    }

    private fun bindToMusicService() {
        Intent(this, MusicService::class.java).also {
            bindService(it, boundServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun unBindMusicService() {
        if (viewModel.isMusicServiceBound) {
            musicService?.runAction(MusicState.STOP)
            unbindService(boundServiceConnection)
            viewModel.isMusicServiceBound = false
        }
    }

    override fun play() {
        Log.d(TAG, "Play")
        musicService?.runAction(MusicState.PlAY)
    }

    override fun pause() {
        Log.d(TAG, "Pause")
        musicService?.runAction(MusicState.PAUSE)
    }

    override fun playNext() {
        Log.d(TAG, "Play next")
        musicService?.runAction(MusicState.PLAY_NEXT)
    }

    override fun playPrevious() {
        Log.d(TAG, "Play previous")
        musicService?.runAction(MusicState.PLAY_PREVIOUS)
    }

    private companion object {
        const val TAG = "ActivityMain"
    }
}
