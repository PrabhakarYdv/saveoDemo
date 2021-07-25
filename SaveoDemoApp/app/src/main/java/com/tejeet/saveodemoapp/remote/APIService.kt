package com.tejeet.mvvmcoroutine.remote
import com.tejeet.nobrokerdemoapi.constants.ConstantsData
import com.tejeet.saveodemoapp.dataModel.MovieResponseDTO
import com.tejeet.saveodemoapp.dataModel.PageResponseDTO
import retrofit2.http.*

interface APIService {


    // Get All Movies

    @Headers("Accept: application/json")
    @GET(ConstantsData.SEARCH_END_POINT)
    suspend fun getAllMoview(
        @Header("Content-Type") contentType: String,
        @Query("q") movieQuery : String,
    ) : List<MovieResponseDTO>


    @Headers("Accept: application/json")
    @GET(ConstantsData.PAGE_END_POINT)
    suspend fun getMoviewByPage(
        @Header("Content-Type") contentType: String,
        @Query("page") pageNo : String,
    ) : List<PageResponseDTO>


}