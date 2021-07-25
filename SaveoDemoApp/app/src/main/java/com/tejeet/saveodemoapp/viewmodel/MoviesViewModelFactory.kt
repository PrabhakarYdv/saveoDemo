package com.tejeet.saveodemoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tejeet.saveodemoapp.repository.MovieDataRepository

class MoviesViewModelFactory(val repository: MovieDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MoviesViewModel(repository) as T
    }
}