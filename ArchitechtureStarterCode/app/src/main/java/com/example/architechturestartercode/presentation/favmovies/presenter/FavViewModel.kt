package com.example.architechturestartercode.presentation.favmovies.presenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.architechturestartercode.data.movie.MoviesRepository
import com.example.architechturestartercode.data.movie.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavViewModel(app: Application, ) : AndroidViewModel(app) {

    private val moviesRepository = MoviesRepository(app)

    val favMovies: StateFlow<List<Movie>> = moviesRepository.getAllFavMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _onDeleteFromFavSuccess = MutableStateFlow<Boolean?>(null)
    val onDeleteFromFavSuccess: StateFlow<Boolean?> = _onDeleteFromFavSuccess.asStateFlow()

//    fun getFavMovies(): LiveData<List<Movie>> {
//        return moviesRepository.getAllFavMovies()
//    }

    fun deleteFavMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                moviesRepository.deleteMovieFromFav(movie)
                _onDeleteFromFavSuccess.value = true
            } catch (e: Exception) {
                _onDeleteFromFavSuccess.value = false
            }
        }
    }
}

