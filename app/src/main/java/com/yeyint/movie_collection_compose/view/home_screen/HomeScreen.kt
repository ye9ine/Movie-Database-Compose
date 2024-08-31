package com.yeyint.movie_collection_compose.view.home_screen
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.model.PreviewMovieAndPosterModel
import com.yeyint.movie_collection_compose.helper.MyColor
import com.yeyint.movie_collection_compose.navigationController.Screen
import com.yeyint.movie_collection_compose.preview_provider.PreviewDataProvider
import com.yeyint.movie_collection_compose.shareViewModel.ShareViewModel
import com.yeyint.movie_collection_compose.view.home_screen.appbar.AppBar
import com.yeyint.movie_collection_compose.view.home_screen.home_list_view.MovieItem
import com.yeyint.movie_collection_compose.view.home_screen.home_list_view.MovieList
import com.yeyint.movie_collection_compose.view.home_screen.search_bar.SearchBarView
import com.yeyint.movie_collection_compose.view.home_screen.up_coming_movie_header.UpcomingMovieHeader
import com.yeyint.movie_collection_compose.viewModel.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    movieViewModel: MovieViewModel = hiltViewModel(),
    shareViewModel: ShareViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
){

    val scope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MyColor.colorPrimary),
    ) {

        AppBar()

        Scaffold(
            modifier = Modifier.background(color = MyColor.colorWhite),
            floatingActionButton = {
                AnimatedVisibility(visible = showButton) {
                    FloatingActionButtonView(scope = scope, listState = listState)
                }

            },
            topBar = {

                SearchBarView(
                    onSearchKeyChange = {
                        movieViewModel.searchKey.value = it
                    },
                    isMovie = movieViewModel.isMovie.value,
                    onGetMovie = {
                        movieViewModel.getMovie()
                    },
                    onGetTv = {
                        movieViewModel.getTv()
                    },
                    onInsertSearchHistory = {
                        movieViewModel.searchKey.value = it
                        movieViewModel.insertSearchHistory(it)
                    },
                    onGetSearchHistory = {
                        movieViewModel.getSearchHistory()
                    },
                    searchList = movieViewModel.searchList
                )
            }
        ) { padding->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxHeight()
                    .background(color = MyColor.colorPrimary)
            ) {
                MovieList(
                    upcomingMovies = movieViewModel.upcomingMovieList,
                    moviesOrTv = if(movieViewModel.isMovie.value) movieViewModel.movie.collectAsLazyPagingItems() else movieViewModel.series.collectAsLazyPagingItems(),
                    isMovie = movieViewModel.isMovie.value,
                    onMovieItemClick = { movie ->
                        shareViewModel.setMovie(movie)
                        navController.navigate(Screen.MovieDetail.name)
                    },
                    onUpComingMovieItemClick = { movie ->
                        shareViewModel.setMovie(movie)
                        navController.navigate(Screen.MovieDetail.name)
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                    lazyListState = listState,
                    onMovieClick = {
                        movieViewModel.isMovie.value = true
                        movieViewModel.getMovie()

                    },
                    onTvClick = {
                        movieViewModel.isMovie.value = false
                        movieViewModel.getTv()

                    }

                )
            }
        }

    }
}

@Composable
fun FloatingActionButtonView(
    scope: CoroutineScope,
    listState: LazyListState
) {
    FloatingActionButton(
        onClick = {
            scope.launch {
                listState.animateScrollToItem(0)
            }
        }) {
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = "up icon",
            Modifier.size(20.dp)
        )
    }
}




@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(@PreviewParameter(PreviewDataProvider::class) previewMovieAndPosterModel: PreviewMovieAndPosterModel) {

    val movie = MovieModel(
        id = previewMovieAndPosterModel.id,
        adult = false,
        backdropPath = previewMovieAndPosterModel.backdropPath,
        originalLanguage = null,
        originalTitle = previewMovieAndPosterModel.originalTitle,
        originalName = previewMovieAndPosterModel.originalName,
        popularity = previewMovieAndPosterModel.popularity,
        posterPath = previewMovieAndPosterModel.posterPath,
        releaseDate = previewMovieAndPosterModel.releaseDate,
        title = previewMovieAndPosterModel.title,
        voteAverage = 6.3,
        voteCount = 0,
        video = false,
        firstAirDate = previewMovieAndPosterModel.firstAirDate,
        overview = previewMovieAndPosterModel.overview,
        type = ""
    )

    val movies = flowOf(PagingData.from(listOf(movie, movie))).collectAsLazyPagingItems()
    val upcomingMovies = mutableListOf(movie)

    val scope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    SharedTransitionLayout {
        Scaffold(
            floatingActionButton = {
                FloatingActionButtonView(scope = scope, listState =listState)
            }
        ) { padding->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MyColor.colorPrimary)
                    .padding(padding),
            ) {

                AppBar()

                LazyColumn{

                    item{
                        SearchBarView(
                            onSearchKeyChange = {

                            },
                            isMovie = true,
                            onGetMovie = {

                            },
                            onGetTv = {

                            },
                            onInsertSearchHistory = {

                            },
                            onGetSearchHistory = {

                            },
                            searchList = emptyList()
                        )
                    }

                    item {
                        UpcomingMovieHeader(
                            upcomingMovies = upcomingMovies,
                            isMovie = true,
                            onUpComingMovieClick = {

                            },
                            onMovieClick = {

                            },
                            onTvClick = {

                            }
                        )
                    }

                    items(
                        count = movies.itemCount,
                    ) {

                        movies[it]?.let { it1 ->
                            MovieItem(
                                movieModel = it1,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = null
                            )
                        }
                    }
                }
            }
        }
    }


}