package com.tejeet.saveodemoapp.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO
import com.tejeet.saveodemoapp.ui.listners.MovieClickListner
import kotlinx.android.synthetic.main.movies_item_layout.view.*

class MoviewViewHolder(private val view : View, val itemClickListner : MovieClickListner) : RecyclerView.ViewHolder(view) {

    fun setData(movieData : MovieResponseDTO){
        view.apply {

            Glide.with(this)
                .load(movieData.show?.image?.medium)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_movie_banner)

            iv_movies_card.setOnClickListener {
                itemClickListner.onItemClick(movieData)
            }

        }
    }

}