package com.yeyint.movie_collection.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.model.TvSeriesModel
import com.yeyint.movie_collection.dao.TvSeriesDao
import com.yeyint.movie_collection_compose.apiService.ApiService
import com.yeyint.movie_collection_compose.helper.MovieConstant

class PagingSearchTvSeriesDataSource(
    private val apiService: ApiService,
    private val tvSeriesDao: TvSeriesDao,
    private val searchKey: String
) : PagingSource<Int, MovieModel>() {

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        try {
            val currentLoadingPageKey = params.key ?: 1

            val response = apiService.searchTvSeries(searchKey, currentLoadingPageKey)
            val responseData = mutableListOf<MovieModel>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)

            responseData.map {
                it.type = MovieConstant.tvSeries
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
            val list = mutableListOf<MovieModel>()
            for (i in tvSeriesDao.searchTvSeries(searchKey)) {
                i.type = MovieConstant.tvSeries
                val movieModel = MovieModel(id = i.id, i.adult, i.backdropPath, i.originalLanguage, i.originalTitle, i.overview, i.popularity, i.posterPath, i.releaseDate,
                    i.title, i.video, i.voteAverage, i.voteCount, i.originalName, i. firstAirDate, i.type)
                list.add(movieModel)
            }
            return LoadResult.Page(
                data = list,
                prevKey = null,
                nextKey = null
            )
        }
    }
}