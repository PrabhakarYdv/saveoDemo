package com.tejeet.saveodemoapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.tejeet.nobrokerdemoapi.constants.ConstantsData
import com.tejeet.saveodemoapp.R
import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        assert(
            supportActionBar != null //null check
        )
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val inData = intent.getSerializableExtra(ConstantsData.DATA_KEY) as? MovieResponseDTO

        supportActionBar!!.title = "${inData?.show?.name} Movies"

        tv_movie_name_details.text = inData?.show?.name
        tv_movie_description.text = Html.fromHtml(inData?.show?.summary)
        rb_movie_ratings.numStars = 5;
        rb_movie_ratings.stepSize = 0.5F
        rb_movie_ratings.rating = inData?.show?.rating?.average!!

        tv_rating_text.text = "${inData?.show?.rating?.average!!}"

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