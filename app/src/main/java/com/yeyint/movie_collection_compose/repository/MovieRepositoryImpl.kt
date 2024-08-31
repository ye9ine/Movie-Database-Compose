package com.yeyint.movie_collection.repository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.model.MovieResultModel
import com.yeyint.movie_collection.model.TvSeriesModel
import com.yeyint.movie_collection.model.UpcomingMovieModel
import com.yeyint.movie_collection.paging.PagingMovieDataSource
import com.yeyint.movie_collection.paging.PagingSearchMovieDataSource
import com.yeyint.movie_collection.paging.PagingSearchTvSeriesDataSource
import com.yeyint.movie_collection.paging.PagingTvSeriesDataSource
import com.yeyint.movie_collection_compose.apiService.ApiService
import com.yeyint.movie_collection_compose.db.MovieDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    movieDb: MovieDb
) : MovieRepository {

    private val movieDao = movieDb.movieDao()
    private val tvSeriesDao = movieDb.tvSeriesDao()
    private val upcomingMovieDao = movieDb.upcomingMovieDao()

    override fun getMovie(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20
            ),
            pagingSourceFactory = {
                PagingMovieDataSource(apiService, movieDao)
            }
        ).flow
    }

    override fun getTv(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20
            ),
            pagingSourceFactory = {
                PagingTvSeriesDataSource(apiService, tvSeriesDao)
            }
        ).flow
    }

    /*override fun getTv(): PagingSource<Int, MovieModel> {
        return PagingTvSeriesDataSource(apiService, tvSeriesDao)
    }*/

    override fun getUpComingMovie(): Flow<Response<MovieResultModel>> {
        return flow {
            emit(apiService.getUpcomingMovie())
        }.flowOn(Dispatchers.IO)
    }

    override fun getMovieFromDb(): Flow<List<MovieModel>> {
        return flow {
            emit(movieDao.getAll())
        }.flowOn(Dispatchers.IO)
    }

    override fun getTvFromDb(): Flow<List<TvSeriesModel>> {
        return flow {
            emit(tvSeriesDao.getAll())
        }.flowOn(Dispatchers.IO)
    }

    override fun getUpcomingMovieFromDb(): Flow<List<UpcomingMovieModel>> {
        return flow {
            emit(upcomingMovieDao.getAll())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertUpcomingMovie(upcomingMovieModel: UpcomingMovieModel) {
        upcomingMovieDao.insert(upcomingMovieModel)
    }

    override fun searchMovie(searchKey: String): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20
            ),
            pagingSourceFactory = {
                PagingSearchMovieDataSource(apiService, movieDao, searchKey)
            }
        ).flow
    }

    override fun searchTv(searchKey: String): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20
            ),
            pagingSourceFactory = {
                PagingSearchTvSeriesDataSource(apiService, tvSeriesDao, searchKey)
            }
        ).flow
    }

    override fun searchMovieFromDb(searchKey: String): Flow<List<MovieModel>> {
        return flow {
            emit(movieDao.searchMovie(searchKey))
        }.flowOn(Dispatchers.IO)
    }

    override fun searchTvFromDb(searchKey: String): Flow<List<TvSeriesModel>> {
        return flow {
            emit(tvSeriesDao.searchTvSeries(searchKey))
        }.flowOn(Dispatchers.IO)
    }


}