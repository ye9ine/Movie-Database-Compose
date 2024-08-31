package com.yeyint.movie_collection_compose.viewModel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.*
import androidx.paging.compose.collectAsLazyPagingItems
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.model.SearchHistoryModel
import com.yeyint.movie_collection.model.UpcomingMovieModel
import com.yeyint.movie_collection.repository.MovieRepositoryImpl
import com.yeyint.movie_collection_compose.db.MovieDb
import com.yeyint.movie_collection_compose.helper.MovieConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UpcomingMovieState {
    class Success(val data: List<MovieModel>):UpcomingMovieState()
    class Exception(val t:Throwable):UpcomingMovieState()
    class OnFail(val message:String):UpcomingMovieState()
    data object Loading:UpcomingMovieState()
    data object Empty:UpcomingMovieState()
}

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepositoryImpl: MovieRepositoryImpl,
    private val movieDb: MovieDb
) : ViewModel() {

//    val upcomingMovieList = mutableStateOf<UpcomingMovieState>(UpcomingMovieState.Empty)
    val upcomingMovieList = mutableListOf<MovieModel>()
    private val offlineMovieList = mutableListOf<MovieModel>()
    val searchKey = mutableStateOf("")
    val isMovie = mutableStateOf(true)

    var movie = movieRepositoryImpl.getMovie().cachedIn(viewModelScope)
    var series = movieRepositoryImpl.getTv().cachedIn(viewModelScope)

    private val searchHistoryDao = movieDb.searchHistoryDao()
    val searchList = mutableListOf<SearchHistoryModel>()

    init {
        getUpcomingMovie()
        getMovie()
    }

    fun getMovie(){
        println("get movie")
        movie = if(searchKey.value.isEmpty()) movieRepositoryImpl.getMovie().cachedIn(viewModelScope) else movieRepositoryImpl.searchMovie(searchKey = searchKey.value).cachedIn(viewModelScope)
    }

    fun getTv(){
        println("get tv")
        series = if(searchKey.value.isEmpty()) movieRepositoryImpl.getTv().cachedIn(viewModelScope) else movieRepositoryImpl.searchTv(searchKey = searchKey.value).cachedIn(viewModelScope)
    }

    /*fun getSearchMovie(){
        movie = movieRepositoryImpl.searchMovie(searchKey = searchKey.value).cachedIn(viewModelScope)
    }

    fun getSearchTv(){
        series = movieRepositoryImpl.searchTv(searchKey = searchKey.value).cachedIn(viewModelScope)
    }*/

    /*fun getSearchMovie(q: String): Flow<PagingData<MovieModel>>{
        return movieRepositoryImpl.searchMovie(searchKey = q).cachedIn(viewModelScope)
    }

    fun getSearchTv(q: String): Flow<PagingData<MovieModel>>{
        return movieRepositoryImpl.searchTv(searchKey = searchKey.value).cachedIn(viewModelScope)
    }*/



    fun insertSearchHistory(searchKey: String){
        if(searchKey.isEmpty()) return
        searchList.forEach {
            if(it.searchKey == searchKey){
                return
            }
        }
        viewModelScope.launch {
            searchHistoryDao.insert(SearchHistoryModel(searchKey = searchKey, id = null))
        }
    }

    fun getSearchHistory(){
        searchList.clear()
        viewModelScope.launch {
            searchList.addAll(searchHistoryDao.getAll())
        }
    }

    private fun getUpcomingMovie() {
        viewModelScope.launch {
            movieRepositoryImpl.getUpComingMovie().onStart {

            }.catch {
                getUpcomingMovieFromDb()
            }.collect {
                if (it.isSuccessful) {
                    val respBody = it.body()

                    respBody.let { value ->
                        val list = mutableListOf<MovieModel>()
                        for(i in value!!.results){
                            i.type = MovieConstant.movie
                            list.add(i)
                            insertUpcomingMovie(i)
                        }
                        upcomingMovieList.addAll(list)
//                        upcomingMovieList.value = UpcomingMovieState.Success(data = list)
                    }
                } else {
//                    upcomingMovieList.value = UpcomingMovieState.OnFail("Something went wrong")
                }
            }
        }
    }

    private suspend fun insertUpcomingMovie(movieModel: MovieModel) {
        val model = UpcomingMovieModel(id = movieModel.id, movieModel.adult, movieModel.backdropPath, movieModel.originalLanguage, movieModel.originalTitle, movieModel.overview, movieModel.popularity, movieModel.posterPath, movieModel.releaseDate,
            movieModel.title, movieModel.video, movieModel.voteAverage, movieModel.voteCount, movieModel.originalName, movieModel.firstAirDate, movieModel.type)
        movieRepositoryImpl.insertUpcomingMovie(model)
    }

    private suspend fun getUpcomingMovieFromDb() {
        viewModelScope.launch {
            movieRepositoryImpl.getUpcomingMovieFromDb().collect {
                val list = mutableListOf<MovieModel>()
                for(i in it){
                    val movieModel = MovieModel(id = i.id, i.adult, i.backdropPath, i.originalLanguage, i.originalTitle, i.overview, i.popularity, i.posterPath, i.releaseDate,
                        i.title, i.video, i.voteAverage, i.voteCount, i.originalName, i. firstAirDate, i.type)
                    list.add(movieModel)
                }
                upcomingMovieList.addAll(list)
//                upcomingMovieList.value = UpcomingMovieState.Success(data = list)
            }
        }
    }

    fun getAllMovieAndTvFromDb(type: String, searchKey: String) {
//        offlineMovieList.value = emptyList()
        viewModelScope.launch {
            if(type == MovieConstant.movie){
                if(searchKey.isNotEmpty()){
                    movieRepositoryImpl.searchMovieFromDb(searchKey).collect{
                        offlineMovieList.addAll(it)
                    }
                }else{
                    movieRepositoryImpl.getMovieFromDb().collect{
                        offlineMovieList.addAll(it)
                        for (i in it) {
                            Log.d("offlinemvoe", i.title!!)
                        }
                    }
                }
            }else{
                if(searchKey.isNotEmpty()){
                    movieRepositoryImpl.getTvFromDb().collect{
                        val list = mutableListOf<MovieModel>()
                        for (i in it) {
                            i.type = MovieConstant.tvSeries
                            val movieModel = MovieModel(id = i.id, i.adult, i.backdropPath, i.originalLanguage, i.originalTitle, i.overview, i.popularity, i.posterPath, i.releaseDate,
                                i.title, i.video, i.voteAverage, i.voteCount, i.originalName, i. firstAirDate, i.type)
                            list.add(movieModel)
                        }
                        offlineMovieList.addAll(list)
                    }
                }else{
                    movieRepositoryImpl.searchTvFromDb(searchKey).collect{
                        val list = mutableListOf<MovieModel>()
                        for (i in it) {
                            i.type = MovieConstant.tvSeries
                            val movieModel = MovieModel(id = i.id, i.adult, i.backdropPath, i.originalLanguage, i.originalTitle, i.overview, i.popularity, i.posterPath, i.releaseDate,
                                i.title, i.video, i.voteAverage, i.voteCount, i.originalName, i. firstAirDate, i.type)
                            list.add(movieModel)
                        }
                        offlineMovieList.addAll(list)
                    }
                }

            }


        }
    }
}