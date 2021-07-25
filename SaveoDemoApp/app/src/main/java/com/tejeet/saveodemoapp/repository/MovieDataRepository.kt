package com.tejeet.saveodemoapp.repository

import androidx.lifecycle.LiveData
import com.tejeet.mvvmcoroutine.remote.APIService
import com.tejeet.mvvmcoroutine.remote.Resource
import com.tejeet.mvvmcoroutine.remote.ResponseHandler
import com.tejeet.mvvmcoroutine.remote.RetrofitGenerator
import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO
import com.tejeet.saveodemoapp.dataModel.PageResponseDTO

class MovieDataRepository() {

    private val CONTENT_TYPE = "application/json"

    val api : APIService = RetrofitGenerator.getInstance().create(APIService::class.java)
    val responseHandler = ResponseHandler()


    suspend fun getMovies(movieQuery : String) : Resource<List<MovieResponseDTO>>{

        val result : List<MovieResponseDTO> = api.getAllMoview(CONTENT_TYPE, movieQuery)

        return  responseHandler.handleSuccess(result)

    }

    suspend fun getMoviewByPage() : Resource<List<PageResponseDTO>>{

        val result : List<PageResponseDTO> = api.getMoviewByPage(CONTENT_TYPE, "1")

        return  responseHandler.handleSuccess(result)

    }

}