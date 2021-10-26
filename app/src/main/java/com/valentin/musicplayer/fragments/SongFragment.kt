package com.valentin.musicplayer.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.valentin.musicplayer.application.appComponent
import com.valentin.musicplayer.databinding.FragmentSongBinding
import com.valentin.musicplayer.utils.TimeUtils
import com.valentin.musicplayer.viewmodel.MainViewModel
import com.valentin.musicplayer.viewmodel.MainViewModelFactory
import javax.inject.Inject

class SongFragment : Fragment() {

    private var _binding: FragmentSongBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

//    private val viewModel: MainViewModel by lazy {
//        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
//    }

    private lateinit var listener: SongFragmentListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext().appComponent.inject(this)
        _binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        binding.apply {
            seekBar.setOnTouchListener { _, _ -> true }
            ivPlay.setOnClickListener {
                listener.play()
            }
            ivPause.setOnClickListener {
                listener.pause()
            }
            ivNext.setOnClickListener {
                listener.playNext()
            }
            ivPrevious.setOnClickListener {
                listener.playPrevious()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as SongFragmentListener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)
        viewModel.currentSong.observe(viewLifecycleOwner) { song ->
            Log.d(TAG, "Observe. Song: $song")
            binding.apply {
                tvSongName.text = song.name
                tvSongPerformer.text = song.artist
                tvCurrent.text = TimeUtils.startTime
                seekBar.progress = 0
                seekBar.max = song.duration.toInt()
                Glide.with(requireContext())
                    .load(song.imageUrl)
                    .into(ivSong)
            }
        }
        viewModel.currentTime.observe(viewLifecycleOwner) { time ->
            binding.seekBar.progress = time.toInt()
            binding.tvCurrent.text = TimeUtils.timeStr(time)
        }
    }

    interface SongFragmentListener {
        fun play()
        fun pause()
        fun playNext()
        fun playPrevious()
    }

    private companion object {
        const val TAG = "SongFragment"
    }
}
