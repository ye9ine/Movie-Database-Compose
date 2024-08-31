package com.yeyint.movie_collection_compose.view.movie_detail_screen.movie_poster

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.yeyint.movie_collection.model.MoviePosterModel
import com.yeyint.movie_collection.model.PreviewMovieAndPosterModel
import com.yeyint.movie_collection_compose.R
import com.yeyint.movie_collection_compose.helper.MovieConstant
import com.yeyint.movie_collection_compose.helper.MyColor
import com.yeyint.movie_collection_compose.helper.MyFontSize
import com.yeyint.movie_collection_compose.preview_provider.PreviewDataProvider

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MoviePoster(
    posterList: List<MoviePosterModel>,
    onPosterClick: (String)-> Unit
){

    Column(
        modifier = Modifier.padding(start = 20.dp)
    ) {

        Text(
            text = "Poster",
            fontSize = MyFontSize.textSizeMedium.sp,
            color = MyColor.colorWhite,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))


        LazyRow{
            items(if(posterList.size < 5) posterList.size else 5) { item ->

                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .clip(RoundedCornerShape(7.dp))
                ){
                    GlideImage(
                        model = MovieConstant.basePosterPath + posterList[item].file_path,
                        contentDescription = "movie_poster",
                        contentScale = ContentScale.Crop,
                        loading = placeholder(R.mipmap.placeholder),
                        failure = placeholder(R.mipmap.placeholder),
                        modifier = Modifier
                            .height(200.dp)
                            .width(130.dp)
                            .clickable { onPosterClick(posterList[item].file_path) }
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))

            }
        }


        Spacer(modifier = Modifier.height(20.dp))



    }
}


@Preview(showBackground = true, backgroundColor = 0xff06284f)
@Composable
fun MoviePosterPreview(@PreviewParameter(PreviewDataProvider::class) previewMovieAndPosterModel: PreviewMovieAndPosterModel) {

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


    MoviePoster(posterList = listOf(posterModel, posterModel, posterModel, posterModel)) {

    }
}