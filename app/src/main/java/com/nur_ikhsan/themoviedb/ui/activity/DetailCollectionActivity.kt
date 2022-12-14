package com.nur_ikhsan.themoviedb.ui.activity

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nur_ikhsan.themoviedb.BuildConfig
import com.nur_ikhsan.themoviedb.R
import com.nur_ikhsan.themoviedb.databinding.ActivityDetailCollectionBinding
import com.nur_ikhsan.themoviedb.adapter.CollectionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailCollectionActivity : AppCompatActivity(){

    private lateinit var binding : ActivityDetailCollectionBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        ((View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN).also { window.decorView.systemUiVisibility = it })
        @Suppress("DEPRECATION")
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT

        val extras = intent.extras
        if (extras != null){
            val collectionId = extras.getString(COLLECTION_ID)
            if (collectionId != null){
                initDetailCollection(collectionId)
                initPartsOfCollection(collectionId)
            }
        }
    }

    private fun initPartsOfCollection(collectionId: String) {
        viewModel.getPartsOfCollection(collectionId = collectionId).observe(this){ parts->
            val collectionAdapter = CollectionAdapter(parts)
            if (parts != null){
                binding.apply {
                    rvCollection.adapter = collectionAdapter
                    if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                        rvCollection.layoutManager = GridLayoutManager(this@DetailCollectionActivity, 3)
                    }else{
                        rvCollection.layoutManager = GridLayoutManager(this@DetailCollectionActivity, 6)
                    }
                }
            }
        }
    }

    private fun initDetailCollection(collectionId: String) {
        viewModel.getDetailCollection(collectionId = collectionId).observe(this){ collection->
            if (collection != null){
                Glide.with(this)
                    .load(Uri.parse(BuildConfig.URL_IMAGE + collection.backdropPath))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.bg_image)
                    .transition(DrawableTransitionOptions.withCrossFade(100))
                    .into(binding.imageCollection)

                setSupportActionBar(binding.toolbarMovie)
                assert(supportActionBar != null)
                supportActionBar?.title = collection.name
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }


    companion object{
        const val COLLECTION_ID = "collection_id"

        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val winParams = window.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            window.attributes = winParams
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}