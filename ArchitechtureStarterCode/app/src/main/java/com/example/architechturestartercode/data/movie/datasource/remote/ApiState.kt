package com.example.architechturestartercode.data.movie.datasource.remote

import com.example.architechturestartercode.data.movie.model.Movie

sealed class ApiState {
    object Loading : ApiState()
    data class Success(val movies: List<Movie>) : ApiState()
    data class Failure(val msg: String) : ApiState()
}