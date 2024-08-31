package com.yeyint.movie_collection_compose.view.movie_detail_screen.movie_info_card

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.model.PreviewMovieAndPosterModel
import com.yeyint.movie_collection_compose.R
import com.yeyint.movie_collection_compose.helper.MovieConstant
import com.yeyint.movie_collection_compose.helper.MyColor
import com.yeyint.movie_collection_compose.helper.MyFontSize
import com.yeyint.movie_collection_compose.preview_provider.PreviewDataProvider
import com.yeyint.movie_collection_compose.view.movie_detail_screen.back_drop_poster.BackDropPoster

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MovieInfoCard(
    movieModel: MovieModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope?,
    onMovieImageClick:()-> Unit
){

    val releaseYear = if (movieModel.type == MovieConstant.movie) movieModel.releaseDate?.split('-')?.get(0) else movieModel.firstAirDate?.split('-')?.get(0)
    val movieTitle = if (movieModel.type == MovieConstant.movie) "${movieModel.title} (${releaseYear})" else "${movieModel.originalName} (${releaseYear})"
    val ratingBar = movieModel.voteAverage?.div(2).toString().toFloat()
    val rating = String.format("%.1f", movieModel.voteAverage)
    val overView = movieModel.overview

    Card(
        modifier = Modifier
            .padding(top = 220.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MyColor.colorPrimary,
        ),
    ) {
        Column(
            modifier = Modifier.padding(start = 10.dp, top = 130.dp, bottom = 20.dp)
        ){
            Text(
                text = overView!!,
                fontSize = MyFontSize.textSizeDefault.sp,
                color = MyColor.colorWhite,
            )

        }
    }

    Box(
        modifier = Modifier
            .padding(top = (250 - 90).dp, start = 30.dp)
    ) {

        with(sharedTransitionScope){
            Row {

                Box(
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                ) {

                    GlideImage(
                        model = MovieConstant.basePosterPath + movieModel.posterPath,
                        contentDescription = "movie_poster",
                        contentScale = ContentScale.Crop,
                        loading = placeholder(R.mipmap.placeholder),
                        failure = placeholder(R.mipmap.placeholder),
                        modifier = if(animatedContentScope != null) Modifier.Companion
                            .sharedElement(
                                state = sharedTransitionScope.rememberSharedContentState(key = "image-${movieModel.id}"),
                                animatedVisibilityScope = animatedContentScope
                            )
                            .height(180.dp)
                            .width(120.dp)
                            .clickable { onMovieImageClick() }
                        else Modifier
                            .height(180.dp)
                            .width(120.dp)
                    )

                }


                Box(
                    modifier = Modifier.padding(top = 70.dp, start = 10.dp)
                ) {
                    Column {

                        Text(
                            text = movieTitle,
                            fontSize = MyFontSize.textSizeMedium.sp,
                            color = MyColor.colorWhite,
                            modifier = if(animatedContentScope != null) Modifier.Companion
                                .sharedElement(
                                    state = sharedTransitionScope.rememberSharedContentState(key = "title-${movieModel.id}"),
                                    animatedVisibilityScope = animatedContentScope
                                )else Modifier
                        )

                        Spacer(modifier = Modifier.height(10.dp))


                        Row{

                            Text(
                                text = rating,
                                fontSize = MyFontSize.textSizeDefault.sp,
                                color = MyColor.colorWhite,
                            )

                            Spacer(modifier = Modifier.width(20.dp))


                            RatingBar(
                                numOfStars = 5,
                                value = ratingBar,
                                style = RatingBarStyle.Default,
                                onValueChange = {},
                                onRatingChanged = {},
                                size = 18.dp,
                                spaceBetween = 3.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true, backgroundColor = 0xff06284f)
@Composable
fun MovieInfoCardPreview(@PreviewParameter(PreviewDataProvider::class) previewMovieAndPosterModel: PreviewMovieAndPosterModel) {

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
        Box {

            BackDropPoster(
                backDrop = "",
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



}