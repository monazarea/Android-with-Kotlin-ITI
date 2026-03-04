package com.example.architechturestartercode.data.movie

import android.content.Context
import com.example.architechturestartercode.data.movie.datasource.local.MoviesLocalDataSource
import com.example.architechturestartercode.data.movie.datasource.remote.ApiState
import com.example.architechturestartercode.data.movie.datasource.remote.MoviesRemoteDataSource
import com.example.architechturestartercode.data.movie.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch

class MoviesRepository(context: Context) {
    private val remoteDataSource = MoviesRemoteDataSource()
    private val localDataSource = MoviesLocalDataSource(context)

    fun getAllMovies(): Flow<ApiState> = flow {
        emit(ApiState.Loading)

        val result = remoteDataSource.getAllMovies()

        if (result.isSuccess) {
            val movies = result.getOrNull() ?: emptyList()
            emit(ApiState.Success(movies))
        } else {
            val errorMsg = result.exceptionOrNull()?.message ?: "Unknown Error"
            emit(ApiState.Failure(errorMsg))
        }
    }.catch { e ->
        emit(ApiState.Failure(e.message ?: "Network Error"))
    }

    fun getAllFavMovies(): Flow<List<Movie>> {
        return localDataSource.getAllMovies()
    }

    suspend fun insertMovieToFav(movie: Movie) {
        localDataSource.insertMovie(movie)
    }

    suspend fun deleteMovieFromFav(movie: Movie) {
        localDataSource.deleteMovie(movie)
    }
}