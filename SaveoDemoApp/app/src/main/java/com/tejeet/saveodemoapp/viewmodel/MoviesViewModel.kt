package com.tejeet.saveodemoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tejeet.mvvmcoroutine.remote.Resource
import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO
import com.tejeet.saveodemoapp.dataModel.PageResponseDTO
import com.tejeet.saveodemoapp.repository.MovieDataRepository
import kotlinx.coroutines.Dispatchers

class MoviesViewModel(val repo : MovieDataRepository) : ViewModel() {


    fun GetMovies(movieQuery : String) : LiveData<List<MovieResponseDTO>>{

        return liveData(Dispatchers.IO){
            val result : Resource<List<MovieResponseDTO>> = repo.getMovies(movieQuery)

            emit(result.data!!)
        }
    }

    fun GetMovewByPage() : LiveData<List<PageResponseDTO>>{

        return liveData(Dispatchers.IO){
            val result : Resource<List<PageResponseDTO>> = repo.getMoviewByPage()

            emit(result.data!!)
        }
    }

}