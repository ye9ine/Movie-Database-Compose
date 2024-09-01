package com.yeyint.movie_collection_compose.view.home_screen.up_coming_movie_header

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.model.PreviewMovieAndPosterModel
import com.yeyint.movie_collection_compose.R
import com.yeyint.movie_collection_compose.helper.MovieConstant
import com.yeyint.movie_collection_compose.helper.MyColor
import com.yeyint.movie_collection_compose.helper.MyFontSize
import com.yeyint.movie_collection_compose.helper.MyFontSize.textSizeExtraSmall
import com.yeyint.movie_collection_compose.preview_provider.PreviewDataProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun UpcomingMovieHeader(
    upcomingMovies: List<MovieModel>,
    isMovie: Boolean,
    onUpComingMovieClick: (MovieModel) -> Unit,
    onMovieClick:()-> Unit,
    onTvClick:()-> Unit
){

    val page = remember{
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = page.intValue, block = {
        coroutineScope.launch {
            delay(3000)
            page.intValue += 1
            if(page.intValue == upcomingMovies.size){
                page.intValue = 0
            }
            pagerState.animateScrollToPage(
                page = page.intValue,
            )

        }
    })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {

        Text(
            text = "Upcoming Movie",
            color = colorResource(id = R.color.white),
            fontSize = MyFontSize.textSizeMedium.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        HorizontalPager(
            count = upcomingMovies.size,
            state = pagerState,
            itemSpacing = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
            ){

                GlideImage(
                    model = MovieConstant.baseBackdropPath + upcomingMovies[page].backdropPath,
                    contentDescription = "movie_poster",
                    contentScale = ContentScale.Crop,
                    loading = placeholder(R.mipmap.placeholder),
                    failure = placeholder(R.mipmap.placeholder),
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clickable { onUpComingMovieClick(upcomingMovies[page]) }
                )

                Box(
                    modifier = Modifier
                        .background(color = MyColor.colorBlackTransparent)
                        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                ) {
                    Text(text = upcomingMovies[page].title?:"-", color = colorResource(id = R.color.white), fontSize = MyFontSize.textSizeExtraSmall.sp,)
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        ChooseMovieSeriesToggle(
            isMovie = isMovie,
            onMovieClick = {
                onMovieClick()
            },
            onTvClick = {
                onTvClick()
            }
        )
    }
}

@Composable
fun ChooseMovieSeriesToggle(
    isMovie: Boolean,
    onMovieClick:()-> Unit,
    onTvClick:()-> Unit
){

    Row {

        Button(
            onClick = { onMovieClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(
                    id = if(isMovie) R.color.color_movie_type_select else R.color.primaryColor
                )
            ),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
        ) {
            Text(
                "In Theaters",
                color = colorResource(id = R.color.white),
                fontSize = textSizeExtraSmall.sp,
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = { onTvClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(
                    id = if(isMovie) R.color.primaryColor else R.color.color_movie_type_select
                )
            ),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
        ) {
            Text(
                "On Tv",
                color = colorResource(id = R.color.white),
                fontSize = textSizeExtraSmall.sp,
            )
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(showBackground = true, backgroundColor = 0xff06284f)
fun UpComingMovieHeaderPreview(@PreviewParameter(PreviewDataProvider::class) previewMovieAndPosterModel: PreviewMovieAndPosterModel) {

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

    val upcomingMovies = mutableListOf(movie)


    LazyColumn{

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
    }
}