package com.tejeet.saveodemoapp.ui

import android.app.Application
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.tejeet.saveodemoapp.repository.MovieDataRepository

class UserApplication : Application() {


    val repository : MovieDataRepository by lazy {
        MovieDataRepository()
    }




}