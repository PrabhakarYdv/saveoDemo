package com.tejeet.saveodemoapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tejeet.saveodemoapp.R
import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO
import com.tejeet.saveodemoapp.ui.listners.MovieClickListner
import com.tejeet.saveodemoapp.ui.viewholder.MoviewViewHolder
import com.tejeet.saveodemoapp.viewmodel.MoviesViewModel

class MoviesAdapter(private var moviesList : List<MovieResponseDTO>, val itemClickListner: MovieClickListner) :
    RecyclerView.Adapter<MoviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movies_item_layout, parent, false)
        return MoviewViewHolder(view, itemClickListner)
    }

    override fun onBindViewHolder(holder: MoviewViewHolder, position: Int) {
        val postData = moviesList[position]
        holder.setData(postData)

    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateData(movieList: List<MovieResponseDTO>) {
        this.moviesList = movieList
        notifyDataSetChanged()
    }

}