package com.tejeet.saveodemoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tejeet.mvvmcoroutine.remote.Resource
import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO
import com.tejeet.saveodemoapp.repository.MovieDataRepository
import kotlinx.coroutines.Dispatchers

class MoviesViewModel(val repo : MovieDataRepository) : ViewModel() {


    fun GetMovies() : LiveData<List<MovieResponseDTO>>{

        return liveData(Dispatchers.IO){
            val result : Resource<List<MovieResponseDTO>> = repo.getMovies()

            emit(result.data!!)
        }
    }

}