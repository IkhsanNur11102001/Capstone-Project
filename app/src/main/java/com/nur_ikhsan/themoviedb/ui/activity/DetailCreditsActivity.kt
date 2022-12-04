package com.nur_ikhsan.themoviedb.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayoutMediator
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.databinding.ActivityDetailCreditsBinding
import com.nur_ikhsan.themoviedb.ui.movie.adapter.PagerAdapterDetailCredits
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailCreditsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailCreditsBinding
    private val viewModel by viewModels<DetailViewModel>()
    private lateinit var pagerAdapterDetailCredits: PagerAdapterDetailCredits

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCreditsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent?.extras
        if (extras != null){
            val creditsId = extras.getString(CREDITS_ID)
            val creditsName = extras.getString(CREDITS_NAME)
            if (creditsId != null && creditsName != null){

                initDetailCredits(creditsId, creditsName)
            }
        }

        pagerAdapterDetailCredits = PagerAdapterDetailCredits(supportFragmentManager, lifecycle)
        binding.apply {
            viewPagerDetail.adapter = pagerAdapterDetailCredits
            TabLayoutMediator(tabLayoutDetail, viewPagerDetail){ tab, position->
                when(position){
                    0-> tab.text = "About"
                    1-> tab.text = "Movie"
                }
            }.attach()
        }
    }

    private fun initDetailCredits(creditsId: String, creditsName: String) {
        binding.toolbarMovie.title = creditsName
        binding.toolbarMovie.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.toolbarMovie.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.btn_share -> Intent(Intent.ACTION_SEND).also { intent ->
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, "$SHARE$creditsId")
                    startActivity(Intent.createChooser(intent, "Share with..."))
                }

                R.id.btn_wiki -> Intent(Intent.ACTION_VIEW).also { intent ->
                    intent.data = Uri.parse("$WIKIPEDIA$creditsName")
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }

                R.id.btn_tmdb -> Intent(Intent.ACTION_VIEW).also { intent ->
                    intent.data = Uri.parse("$TMDB_MOVIE$creditsId")
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }

                R.id.btn_google -> Intent(Intent.ACTION_VIEW).also { intent ->
                    intent.data = Uri.parse("$GOOGLE$creditsName")
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
            }
            true
        }

        viewModel.getDetailCredits(creditsId).observe(this){ credits->
            if (credits != null){
                binding.apply {
                    tvNameCredits.text = creditsName
                    tvJobOrCharacter.text = credits.knownForDepartment
                    imageCredits.load("https://image.tmdb.org/t/p/w185/${credits.profilePath}"){
                        crossfade(true)
                        crossfade(100)
                        transformations(CircleCropTransformation())
                        error(R.drawable.ic_profile)
                    }
                }
            }
        }
    }

    companion object{
        const val CREDITS_ID = "credits_id"
        const val CREDITS_NAME = "credits_name"
        const val SHARE = "https://www.themoviedb.org/person/"
        const val GOOGLE = "https://www.google.com/search?q="
        const val WIKIPEDIA = "https://en.wikipedia.org/wiki/"
        const val TMDB_MOVIE = "https://www.themoviedb.org/person/"

    }
}