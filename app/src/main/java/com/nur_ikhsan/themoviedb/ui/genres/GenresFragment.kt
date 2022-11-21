package com.nur_ikhsan.themoviedb.ui.genres

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nur_ikhsan.themoviedb.databinding.FragmentGenresBinding
import com.nur_ikhsan.themoviedb.ui.genres.adapter.GenresAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GenresFragment : Fragment() {

    private var _binding : FragmentGenresBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<GenresViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {

        viewModel.genres.observe(viewLifecycleOwner){ genres->
            val adapter = GenresAdapter(genres)
            binding.apply {
                rvGenres.adapter = adapter
                rvGenres.layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}