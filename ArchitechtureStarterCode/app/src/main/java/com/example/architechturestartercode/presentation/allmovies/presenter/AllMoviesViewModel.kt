package com.example.architechturestartercode.presentation.allmovies.presenter

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.architechturestartercode.data.movie.MoviesRepository
import com.example.architechturestartercode.data.movie.datasource.remote.ApiState
import com.example.architechturestartercode.data.movie.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllMoviesViewModel(val moviesRepository: MoviesRepository) : ViewModel(){

    //private val moviesRepository = MoviesRepository(app)


//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean>
//        get() = _isLoading
//
//    private val _allMovies = MutableLiveData<List<Movie>>()
//    val allMovies: LiveData<List<Movie>>
//        get() = _allMovies
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String>
//        get() = _error
//
//    private val _addedToFavSuccess = MutableLiveData<Boolean>()
//    val addedToFavSuccess: LiveData<Boolean> = _addedToFavSuccess

    private val _moviesState = MutableStateFlow<ApiState>(ApiState.Loading)
    val moviesState: StateFlow<ApiState> = _moviesState.asStateFlow()


    private val _addedToFavSuccess = MutableStateFlow<Boolean?>(null)
    val addedToFavSuccess = _addedToFavSuccess.asStateFlow()

    init {
        getAllMovies()
    }
//    val job = Job()
//    val scope = CoroutineScope(job+ Dispatchers.Main)

//    fun getAllMovies() {
//        _isLoading.value = true
//        viewModelScope.launch {
//            val result = moviesRepository.getAllMovies()
//            if(result.isSuccess){
//                _isLoading.value = false
//                _allMovies.value = result.getOrNull()
//            }else{
//                _isLoading.value = false
//                _error.value = "Couldn't load moveis, ${result.exceptionOrNull()?.message}"
//            }
//        }
//    }
fun getAllMovies(){
    viewModelScope.launch {
        moviesRepository.getAllMovies().collect { state ->
            _moviesState.value = state
        }
    }
}

    fun addToFav(movie: Movie) {
        viewModelScope.launch {
            try {
                moviesRepository.insertMovieToFav(movie)
                _addedToFavSuccess.value = true

            }catch (e: Exception){
                _addedToFavSuccess.value = false
              //  _error.value = "Couldn't add movie,${e.message}"
            }
        }
    }

//    override fun onCleared() {
//        super.onCleared()
//        scope.cancel()
//    }

}
class AllMoviesViewModelFactory(val repo: MoviesRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllMoviesViewModel(repo) as T
    }

}
