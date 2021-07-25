package com.tejeet.saveodemoapp.ui.activities

import android.content.BroadcastReceiver
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.picasso.PicassoImageLoaderFactory
import com.tejeet.nobrokerdemoapi.constants.ConstantsData
import com.tejeet.saveodemoapp.R
import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO
import com.tejeet.saveodemoapp.dataModel.PageResponseDTO
import com.tejeet.saveodemoapp.repository.MovieDataRepository
import com.tejeet.saveodemoapp.ui.Broadcasts.MyNetworkBroadcast
import com.tejeet.saveodemoapp.ui.UserApplication
import com.tejeet.saveodemoapp.ui.adapters.MoviesAdapter
import com.tejeet.saveodemoapp.ui.listners.MovieClickListner
import com.tejeet.saveodemoapp.ui.listners.NetworkListner
import com.tejeet.saveodemoapp.viewmodel.MoviesViewModel
import com.tejeet.saveodemoapp.viewmodel.MoviesViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , MovieClickListner, NetworkListner {

    private lateinit var moviesAdapter: MoviesAdapter
    private var moviesListDaa: List<PageResponseDTO> = listOf()

    private var mNetworkReceiver: BroadcastReceiver? = null

    private val TAG = "tag"

    private lateinit var imageSlider: ImageSlider

    private var imageUrls = ArrayList<String>()

    private lateinit var viewModel : MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerAdatapter()

        imageSlider = image_slider

        mNetworkReceiver = MyNetworkBroadcast(this, this)

        registerReceiver(mNetworkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        shimmerFrameLayout.startShimmerAnimation()
        shimmerFrameLayout.visibility = View.VISIBLE
        rcvMovies.visibility = View.GONE

        val appObj  = application as UserApplication

        val reposotory : MovieDataRepository = appObj.repository
        val viewModelFactory : MoviesViewModelFactory = MoviesViewModelFactory(reposotory)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)


        if (isNetworkConnected()){
            viewModel.GetMovies("romantic").observe(this, {

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
            viewModel.GetMovewByPage().observe(this,{

                val resp: List<PageResponseDTO> = it!!

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


    override fun onDestroy() {
        unregisterReceiver(mNetworkReceiver)
        super.onDestroy()

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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_movie, menu)
        val item = menu.findItem(R.id.search_movie)
        val searchView = item.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMovies(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    fun filterMovies(movieQuery : String ){

        if (isNetworkConnected()){
            viewModel.GetMovies(movieQuery).observe(this, {

                if (it.size > 0){

                    imageUrls.clear()

                    val image : List<MovieResponseDTO> = it!!
                    image.forEach {
                        it?.show?.image?.original?.let { it1 -> imageUrls.add(it1) }
                    }

                    imageSlider.adapter?.notifyDataSetChanged()
                }


            })

            Log.d(TAG, "List size is ${imageUrls.size}")

        }
    }



    private fun setRecyclerAdatapter() {
        moviesAdapter = MoviesAdapter(moviesListDaa, this)
        val gridLayoutManager = GridLayoutManager(this, 3 )
        rcvMovies.layoutManager = gridLayoutManager
        rcvMovies.adapter = moviesAdapter
    }


    override fun onItemClick(data: PageResponseDTO) {

        Log.d(TAG, "Movie Clicked is ${data?.externals?.tvrage}")
        goToMovieDetails(data)

    }

    private fun goToMovieDetails(movieData: PageResponseDTO) {

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

    override fun onNetworkChnageUpdates(data: String) {
        Log.d(TAG, "Network is ${data}")

        if (moviesListDaa.isEmpty() && data.equals("YES")){


            Toast.makeText(this, "Network Arrived Requesting Data ", Toast.LENGTH_SHORT)
                .show()

            shimmerFrameLayout.startShimmerAnimation()
            shimmerFrameLayout.visibility = View.VISIBLE
            rcvMovies.visibility = View.GONE

            viewModel.GetMovewByPage().observe(this,{

                val resp: List<PageResponseDTO> = it!!

                Log.d(TAG, "Response is ${resp.size}")
                moviesListDaa = resp;
                moviesAdapter.updateData(resp)
                shimmerFrameLayout.stopShimmerAnimation()
                shimmerFrameLayout.visibility = View.GONE
                rcvMovies.visibility = View.VISIBLE

            })
        }
    }
}