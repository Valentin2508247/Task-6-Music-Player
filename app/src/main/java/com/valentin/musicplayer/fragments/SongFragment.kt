package com.valentin.musicplayer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.valentin.musicplayer.databinding.FragmentSongBinding
import com.valentin.musicplayer.viewmodel.MainViewModel

class SongFragment : Fragment() {

    private var _binding: FragmentSongBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private lateinit var listener: SongFragmentListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
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
