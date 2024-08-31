package com.yeyint.movie_collection_compose.view.home_screen.home_list_view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.model.PreviewMovieAndPosterModel
import com.yeyint.movie_collection_compose.R
import com.yeyint.movie_collection_compose.helper.MovieConstant
import com.yeyint.movie_collection_compose.helper.MyColor
import com.yeyint.movie_collection_compose.helper.MyFontSize
import com.yeyint.movie_collection_compose.preview_provider.PreviewDataProvider
import com.yeyint.movie_collection_compose.view.home_screen.up_coming_movie_header.UpcomingMovieHeader
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieList(
    upcomingMovies: List<MovieModel>,
    moviesOrTv: LazyPagingItems<MovieModel>,
    isMovie: Boolean,
    onMovieItemClick: (MovieModel) -> Unit,
    onUpComingMovieItemClick: (MovieModel) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    lazyListState: LazyListState,
    onMovieClick:()-> Unit,
    onTvClick:()-> Unit
){

    moviesOrTv.apply {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                Column {

                    LinearProgressIndicator(
                        color = colorResource(id = R.color.white),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    UpcomingMovieHeader(
                        upcomingMovies = upcomingMovies,
                        isMovie = isMovie,
                        onUpComingMovieClick = {movie ->
                            onUpComingMovieItemClick(movie)
                        },
                        onMovieClick = {
                            onMovieClick()
                        },
                        onTvClick = {
                            onTvClick()
                        }
                    )
                }
            }

            is LoadState.Error -> {

            }

            else -> {
                LazyColumn(
                    state = lazyListState
                ){

                    item {
                        UpcomingMovieHeader(
                            upcomingMovies = upcomingMovies,
                            isMovie = isMovie,
                            onUpComingMovieClick = {movie ->
                                onUpComingMovieItemClick(movie)
                            },
                            onMovieClick = {
                                onMovieClick()
                            },
                            onTvClick = {
                                onTvClick()
                            }
                        )
                    }

                    items(
                        count = moviesOrTv.itemCount,
                        /*key = {
                            moviesOrTv[it]!!.id
                        }*/
                    ) {

                        moviesOrTv[it]?.let { it1 ->
                            Box(
                                modifier = Modifier.clickable { onMovieItemClick(it1)}
                            ) {
                                MovieItem(
                                    movieModel = it1,
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedContentScope = animatedContentScope
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MovieItem(
    movieModel: MovieModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope?
){
    Row(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        with(sharedTransitionScope){
            Box {

                GlideImage(
                    model = MovieConstant.baseBackdropPath + movieModel.posterPath,
                    contentDescription = "movie_poster",
                    contentScale = ContentScale.Crop,
                    loading = placeholder(R.mipmap.placeholder),
                    failure = placeholder(R.mipmap.placeholder),
                    modifier = if(animatedContentScope != null) Modifier.Companion
                        .sharedElement(
                            state = sharedTransitionScope.rememberSharedContentState(key = "image-${movieModel.id}"),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .width(120.dp)
                        .height(170.dp)
                    else Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .width(120.dp)
                        .height(170.dp)
                )



                Box(
                    modifier = Modifier
                        .padding(7.dp)
                        .background(color = MyColor.colorYellow, shape = RoundedCornerShape(20.dp))
                        .align(Alignment.BottomEnd),
                ) {
                    Text(
                        text = String.format("%.1f", movieModel.voteAverage),
                        fontSize = MyFontSize.textSizeSmall.sp,
                        color = MyColor.colorBlack,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 1.dp, bottom = 1.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.width(15.dp))

            Column {
                Text(
                    text = movieModel.title ?: movieModel.originalName!!,
                    color = colorResource(id = R.color.white),
                    fontSize = MyFontSize.textSizeMedium.sp,
                    modifier = if(animatedContentScope != null) Modifier.Companion
                        .sharedElement(
                            state = sharedTransitionScope.rememberSharedContentState(key = "title-${movieModel.id}"),
                            animatedVisibilityScope = animatedContentScope
                        ) else Modifier
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = movieModel.releaseDate ?: movieModel.firstAirDate!!,
                    color = colorResource(id = R.color.white),
                    fontSize = MyFontSize.textSizeExtraSmall.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = movieModel.overview!!,
                    color = colorResource(id = R.color.white),
                    fontSize = MyFontSize.textSizeExtraSmall.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }


    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun MovieListPreview(@PreviewParameter(PreviewDataProvider::class) previewMovieAndPosterModel: PreviewMovieAndPosterModel) {
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

    SharedTransitionLayout {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MyColor.colorPrimary),
        ) {

            LazyColumn{

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