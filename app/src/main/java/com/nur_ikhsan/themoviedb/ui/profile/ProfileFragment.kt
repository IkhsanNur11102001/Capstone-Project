package com.nur_ikhsan.themoviedb.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.nur_ikhsan.themoviedb.databinding.FragmentProfileBinding
import com.nur_ikhsan.themoviedb.ui.auth.LoginActivity
import com.nur_ikhsan.themoviedb.ui.favorite.FavoriteMoviesActivity
import com.nur_ikhsan.themoviedb.ui.favorite.FavoriteTrailersActivity
import com.nur_ikhsan.themoviedb.ui.watchlist.WatchlistMoviesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarMovie.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.apply {
            tvWatchlist.setOnClickListener {
                Intent(context, WatchlistMoviesActivity::class.java).also {
                    startActivity(it)
                }
            }

            tvTrailersFavorite.setOnClickListener {
                Intent(context, FavoriteTrailersActivity::class.java).also {
                    startActivity(it)
                }
            }

            tvMoviesFavorite.setOnClickListener {
                Intent(context, FavoriteMoviesActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null){
            binding.toolbarMovie.title = user.displayName
            binding.tvEmail.text = user.email
        }

        binding.btnLogout.setOnClickListener {
            Intent(context, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                auth.signOut()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}