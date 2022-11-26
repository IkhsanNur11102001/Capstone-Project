package com.nur_ikhsan.themoviedb.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nur_ikhsan.themoviedb.databinding.FragmentAboutCreditsBinding
import com.nur_ikhsan.themoviedb.ui.activity.DetailCreditsActivity
import com.nur_ikhsan.themoviedb.ui.activity.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutCreditsFragment : Fragment() {

    private var _binding : FragmentAboutCreditsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutCreditsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val extras = activity?.intent?.extras
        if (extras != null){
            val creditsId = extras.getString(DetailCreditsActivity.CREDITS_ID)
            if (creditsId != null){
                initDetailCredits(creditsId)
            }
        }
    }

    private fun initDetailCredits(creditsId: String) {
        viewModel.getDetailCredits(creditsId = creditsId).observe(viewLifecycleOwner){ detail->
            if (detail != null){
                binding.apply {
                    tvFrom.text = detail.placeOfBirth
                    tvBorn.text = detail.birthday
                    tvBiography.text = detail.biography
                }
            }
        }
    }
}