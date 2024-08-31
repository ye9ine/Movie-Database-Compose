package com.yeyint.movie_collection.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.dao.MovieDao
import com.yeyint.movie_collection_compose.apiService.ApiService
import com.yeyint.movie_collection_compose.helper.MovieConstant

class PagingSearchMovieDataSource(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val searchKey: String
) : PagingSource<Int, MovieModel>() {

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        try {
            val currentLoadingPageKey = params.key ?: 1

            val response = apiService.searchMovie(searchKey, currentLoadingPageKey)
            val responseData = mutableListOf<MovieModel>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)
            responseData.map {
                it.type = MovieConstant.movie
            }

            val nextKey = if (responseData.isEmpty()) null else currentLoadingPageKey + 1

            if(response.body()?.results?.isEmpty() == true){
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            return LoadResult.Page(
                data = responseData,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            val list = movieDao.searchMovie(searchKey)
            return LoadResult.Page(
                data = list,
                prevKey = null,
                nextKey = null
            )
        }
    }
}