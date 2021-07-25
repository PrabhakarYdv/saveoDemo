package com.tejeet.saveodemoapp.ui.listners

import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO
import com.tejeet.saveodemoapp.dataModel.PageResponseDTO

interface MovieClickListner {

    fun onItemClick(data: PageResponseDTO)
}