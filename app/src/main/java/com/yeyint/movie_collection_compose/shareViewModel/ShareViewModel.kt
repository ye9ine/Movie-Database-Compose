package com.yeyint.movie_collection_compose.shareViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeyint.movie_collection.model.MovieModel

class ShareViewModel: ViewModel() {

    private val movieDetail = mutableStateOf<MovieModel?>(null)
    private val image = mutableStateOf<String?>(null)

    fun setMovie(movie: MovieModel?){
        movieDetail.value = movie
    }

    fun getMovie(): MovieModel? = movieDetail.value

    fun setImage(poster: String?){
        image.value = poster
    }

    fun getPoster(): String? = image.value

}