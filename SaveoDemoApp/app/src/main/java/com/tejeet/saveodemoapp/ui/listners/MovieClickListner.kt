package com.tejeet.saveodemoapp.ui.listners

import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO

interface MovieClickListner {

    fun onItemClick(data: MovieResponseDTO)
}