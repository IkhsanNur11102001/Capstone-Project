package com.nur_ikhsan.themoviedb.ui.search

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nur_ikhsan.themoviedb.databinding.FragmentSearchBinding
import com.nur_ikhsan.themoviedb.ui.movie.adapter.AdapterMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            searchMovie.onActionViewExpanded()
            searchMovie.setOnQueryTextListener(this@SearchFragment)
            toolbarMovie.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!!.isNotEmpty()){
            viewModel.getResultMovies(query = query).observe(viewLifecycleOwner){ result->
                val adapterMovies = AdapterMovies()
                adapterMovies.submitData(viewLifecycleOwner.lifecycle, result)
                binding.apply {

                    btnRetry.setOnClickListener {
                        adapterMovies.retry()
                    }

                    rvResultMovie.adapter = adapterMovies

                    adapterMovies.addLoadStateListener { loadState->
                        binding.apply {
                            tvError.isVisible = loadState.source.refresh is LoadState.Error
                            btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                            rvResultMovie.isVisible = loadState.source.refresh is LoadState.NotLoading

                            if (loadState.source.refresh is LoadState.NotLoading &&
                                loadState.append.endOfPaginationReached && adapterMovies.itemCount < 1){
                                rvResultMovie.isVisible = false
                                tvError.isVisible = true
                            }else{
                                tvError.isVisible = false
                            }
                        }
                    }

                    rvResultMovie.isVisible = true
                    rvResultKeyWord.isVisible = false

                    if (context?.applicationContext!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                        rvResultMovie.layoutManager = GridLayoutManager(context, 3)
                    }else{
                        rvResultMovie.layoutManager = GridLayoutManager(context, 6)
                    }
                }
            }

        }else{
            binding.rvResultMovie.isVisible = false
            binding.rvResultKeyWord.isVisible = false
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!!.isNotEmpty()){
            viewModel.getResultKeyword(query = newText).observe(viewLifecycleOwner){ keyWord->
                val adapterKeywords = AdapterKeywords()
                adapterKeywords.submitData(viewLifecycleOwner.lifecycle, keyWord)
                binding.apply {

                    rvResultKeyWord.isVisible = true
                    rvResultMovie.isVisible = false

                    rvResultKeyWord.adapter = adapterKeywords
                    rvResultKeyWord.layoutManager = LinearLayoutManager(context)
                }
            }
        }else{
            binding.rvResultKeyWord.isVisible = false
            binding.rvResultMovie.isVisible = false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}