package com.tejeet.saveodemoapp.ui.activities

import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.picasso.PicassoImageLoaderFactory
import com.tejeet.nobrokerdemoapi.constants.ConstantsData
import com.tejeet.saveodemoapp.R
import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO
import com.tejeet.saveodemoapp.repository.MovieDataRepository
import com.tejeet.saveodemoapp.ui.UserApplication
import com.tejeet.saveodemoapp.ui.adapters.MoviesAdapter
import com.tejeet.saveodemoapp.ui.listners.MovieClickListner
import com.tejeet.saveodemoapp.viewmodel.MoviesViewModel
import com.tejeet.saveodemoapp.viewmodel.MoviesViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , MovieClickListner {

    private lateinit var moviesAdapter: MoviesAdapter
    private var moviesListDaa: List<MovieResponseDTO> = listOf()

    private val TAG = "tag"

    private lateinit var imageSlider: ImageSlider

    private var imageUrls = ArrayList<String>()

    private lateinit var viewModel : MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerAdatapter()

        imageSlider = image_slider

        shimmerFrameLayout.startShimmerAnimation()
        shimmerFrameLayout.visibility = View.VISIBLE
        rcvMovies.visibility = View.GONE

        val appObj  = application as UserApplication

        val reposotory : MovieDataRepository = appObj.repository
        val viewModelFactory : MoviesViewModelFactory = MoviesViewModelFactory(reposotory)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)


        if (isNetworkConnected()){
            viewModel.GetMovies().observe(this, {

                val image : List<MovieResponseDTO> = it!!
                image.forEach {
                    it?.show?.image?.original?.let { it1 -> imageUrls.add(it1) }
                }

                imageSlider.adapter?.notifyDataSetChanged()
            })

            imageUrls.add("http://i.imgur.com/CqmBjo5.jpg")

            imageSlider.adapter = SliderAdapter(
                this,
                PicassoImageLoaderFactory(),
                imageUrls = imageUrls
            )

            Log.d(TAG, "List size is ${imageUrls.size}")

        }

        if(isNetworkConnected()){
            viewModel.GetMovies().observe(this,{

                val resp: List<MovieResponseDTO> = it!!

                Log.d(TAG, "Response is ${resp.size}")
                moviesListDaa = resp;
                moviesAdapter.updateData(resp)
                shimmerFrameLayout.stopShimmerAnimation()
                shimmerFrameLayout.visibility = View.GONE
                rcvMovies.visibility = View.VISIBLE

            })
        }
        else {

            Toast.makeText(this, " No Internet ", Toast.LENGTH_SHORT)
                .show()
        }

    }

    override fun onBackPressed() {
        showCloseAppDialogue()
    }

    private fun showCloseAppDialogue() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Close App ?")
        builder.setMessage("Do You Wants to Close the App")

        builder.setPositiveButton("YES", DialogInterface.OnClickListener { dialog, id ->
            finish()
        })

        builder.setNegativeButton("NO", DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()

        })

        builder.setCancelable(true)
        builder.show()
    }

    private fun setRecyclerAdatapter() {
        moviesAdapter = MoviesAdapter(moviesListDaa, this)
        val gridLayoutManager = GridLayoutManager(this, 3 )
        rcvMovies.layoutManager = gridLayoutManager
        rcvMovies.adapter = moviesAdapter
    }


    override fun onItemClick(data: MovieResponseDTO) {

        Log.d(TAG, "Movie Clicked is ${data.show?.externals?.tvrage}")
        goToMovieDetails(data)

    }

    private fun goToMovieDetails(movieData: MovieResponseDTO) {

        intent = Intent(this, MovieDetails::class.java);
        intent.putExtra(ConstantsData.DATA_KEY, movieData)
        startActivity(intent);
        overridePendingTransition(R.anim.enter_first, R.anim.enter_second);
        finish();
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected

    }
}