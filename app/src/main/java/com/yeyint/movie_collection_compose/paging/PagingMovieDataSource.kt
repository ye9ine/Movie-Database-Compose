package com.yeyint.movie_collection.paging
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.dao.MovieDao
import com.yeyint.movie_collection_compose.apiService.ApiService
import com.yeyint.movie_collection_compose.helper.MovieConstant

class PagingMovieDataSource(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) : PagingSource<Int, MovieModel>() {

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        try {
            val currentLoadingPageKey = params.key ?: 1

            val response = apiService.getMovie(currentLoadingPageKey)
            val responseData = mutableListOf<MovieModel>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)

            for (movie in responseData) {
                movie.type = MovieConstant.movie
                movieDao.insert(movie)
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
            val list = movieDao.getAll()
            return LoadResult.Page(
                data = list,
                prevKey = null,
                nextKey = null
            )
        }
    }
}