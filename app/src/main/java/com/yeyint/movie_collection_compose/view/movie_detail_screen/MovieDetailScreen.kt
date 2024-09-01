package com.yeyint.movie_collection_compose.view.movie_detail_screen

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.model.MoviePosterModel
import com.yeyint.movie_collection.model.PreviewMovieAndPosterModel
import com.yeyint.movie_collection_compose.R
import com.yeyint.movie_collection_compose.helper.MovieConstant
import com.yeyint.movie_collection_compose.helper.MyColor
import com.yeyint.movie_collection_compose.helper.MyFontSize
import com.yeyint.movie_collection_compose.navigationController.Screen
import com.yeyint.movie_collection_compose.preview_provider.PreviewDataProvider
import com.yeyint.movie_collection_compose.shareViewModel.ShareViewModel
import com.yeyint.movie_collection_compose.view.movie_detail_screen.back_drop_poster.BackDropPoster
import com.yeyint.movie_collection_compose.view.movie_detail_screen.movie_info_card.MovieInfoCard
import com.yeyint.movie_collection_compose.view.movie_detail_screen.movie_poster.MoviePoster
import com.yeyint.movie_collection_compose.viewModel.MovieDetailViewModel
import com.yeyint.movie_collection_compose.viewModel.MoviePosterState
import com.yeyint.movie_collection_compose.viewModel.MovieTrailerState


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieDetailScreen(
    navController: NavController,
    shareViewModel: ShareViewModel,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
){

    val posterListState by movieDetailViewModel.movieTvPosterList.observeAsState()
    val trailerListState by movieDetailViewModel.movieTvTrailerList.observeAsState()
    movieDetailViewModel.movieId.value = shareViewModel.getMovie()!!.id
    movieDetailViewModel.type.value = shareViewModel.getMovie()!!.type

    LazyColumn(
        modifier = Modifier
            .background(color = MyColor.colorPrimaryDark)
            .fillMaxHeight()
    ) {
        item {

            Box{

                BackDropPoster(
                    backDrop = shareViewModel.getMovie()!!.backdropPath,
                    onPosterClick = {
                        shareViewModel.setImage(MovieConstant.baseBackdropPath + shareViewModel.getMovie()!!.backdropPath)
                        navController.navigate(Screen.PhotoDetailView.name)
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )

                MovieInfoCard(
                    movieModel = shareViewModel.getMovie()!!,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                    onMovieImageClick = {
                        shareViewModel.setImage(MovieConstant.basePosterHighResolutionPath + shareViewModel.getMovie()!!.posterPath)
                        navController.navigate(Screen.PhotoDetailView.name)
                    }
                )
            }
        }

        when(posterListState){
            MoviePosterState.Empty -> {

            }
            is MoviePosterState.Exception -> {

            }
            MoviePosterState.Loading -> {

            }
            is MoviePosterState.OnFail -> {

            }
            is MoviePosterState.Success -> {
                val list = (posterListState as MoviePosterState.Success).data
                item {
                    MoviePoster(
                        posterList = list,
                        onPosterClick = { poster ->
                            shareViewModel.setImage(MovieConstant.basePosterHighResolutionPath + poster)
                            navController.navigate(Screen.PhotoDetailView.name)
                        },
                    )
                }
            }

            else -> {

            }
        }

        when(trailerListState){
            MovieTrailerState.Empty -> {

            }
            is MovieTrailerState.Exception -> {

            }
            MovieTrailerState.Loading -> {

            }
            is MovieTrailerState.OnFail -> {

            }
            is MovieTrailerState.Success -> {
                val data = (trailerListState as MovieTrailerState.Success).data
                if(data.isNotEmpty()){
                    item {
                        AndroidView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp, end = 20.dp, start = 20.dp),
                            factory = { context ->
                                YouTubePlayerView(context = context).apply {

                                    lifecycleOwner.lifecycle.addObserver(this)

                                    addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
                                        override fun onReady(youTubePlayer: YouTubePlayer) {
                                            super.onReady(youTubePlayer)
                                            youTubePlayer.cueVideo(data.first().key,0f)
                                        }
                                    })
                                }
                            }
                        )
                    }
                }

            }

            else -> {}
        }

    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview(@PreviewParameter(PreviewDataProvider::class) previewMovieAndPosterModel: PreviewMovieAndPosterModel){

    val posterModel = MoviePosterModel(
        id = 0,
        movieId = previewMovieAndPosterModel.movieId,
        aspect_ratio = previewMovieAndPosterModel.aspect_ratio,
        file_path = previewMovieAndPosterModel.file_path,
        height = previewMovieAndPosterModel.height,
        iso_639_1 = previewMovieAndPosterModel.iso_639_1,
        vote_average = previewMovieAndPosterModel.voteAverage!!,
        vote_count = previewMovieAndPosterModel.voteCount!!,
        width = previewMovieAndPosterModel.width
    )

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

    SharedTransitionLayout {
        LazyColumn(
            modifier = Modifier.background(color = MyColor.colorPrimaryDark)
        ) {


            item {

                Box{

                    BackDropPoster(
                        backDrop = previewMovieAndPosterModel.backdropPath,
                        onPosterClick = {

                        },
                        onBackClick = {

                        }
                    )

                    MovieInfoCard(
                        movieModel = movie,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = null,
                        onMovieImageClick = {

                        }
                    )

                }
            }

            item {
                MoviePoster(
                    posterList = listOf(posterModel, posterModel, posterModel, posterModel),
                    onPosterClick = {

                    },
                )
            }

        }
    }
}

