package com.tejeet.saveodemoapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tejeet.nobrokerdemoapi.constants.ConstantsData
import com.tejeet.nobrokerdemoapi.constants.Utils
import com.tejeet.saveodemoapp.R
import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO
import com.tejeet.saveodemoapp.dataModel.PageResponseDTO
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        assert(
            supportActionBar != null //null check
        )
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val inData = intent.getSerializableExtra(ConstantsData.DATA_KEY) as? PageResponseDTO

        supportActionBar!!.title = "${inData?.name}"

        if (inData != null) {
            loadDatatoField(inData)
        }


    }


    private fun loadDatatoField(inData : PageResponseDTO){

        Glide.with(this)
            .load(inData?.image?.medium)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv_movie_banner_details)

        tv_movie_name_details.text = inData?.name
        tv_movie_description.text = Html.fromHtml(inData?.summary)
        rb_movie_ratings.numStars = 5;
        rb_movie_ratings.stepSize = 0.5F
        rb_movie_ratings.rating = inData?.rating?.average!!

        tv_rating_text.text = "${inData?.rating?.average!!}"

        tv_movie_tag_details.text = "${inData.genres}"

        tv_time_n_date.text = " ${inData.type} | ${inData?.averageRuntime?.let { Utils.getTimeConverted(it*60) }}"

    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.exit_first, R.anim.exit_second)
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.exit_first, R.anim.exit_second)
        finish()
    }

}