package com.nur_ikhsan.themoviedb.ui.providers

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.databinding.FragmentProvidersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProvidersFragment : Fragment() {

    private var _binding : FragmentProvidersBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProvidersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProvidersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.providers.observe(viewLifecycleOwner){ providers->
            if (providers!!.isNotEmpty()){
                val providersAdapter = ProvidersAdapter(providers)
                binding.rvProviders.adapter = providersAdapter

                providersAdapter.onSelectData(object : ProvidersAdapter.OnSelectData{
                    override fun itemSelected(providersId: String, name : String) {
                        val intent = Intent(context, MovieProvidersActivity::class.java)
                        intent.putExtra(MovieProvidersActivity.PROVIDERS_ID, providersId)
                        intent.putExtra(MovieProvidersActivity.PROVIDERS_NAME, name)
                        startActivity(intent)
                    }
                })
            }
            if (context?.applicationContext!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                binding.rvProviders.layoutManager = GridLayoutManager(context, 5)
            }else{
                binding.rvProviders.layoutManager = GridLayoutManager(context, 10)
            }
        }
    }
}