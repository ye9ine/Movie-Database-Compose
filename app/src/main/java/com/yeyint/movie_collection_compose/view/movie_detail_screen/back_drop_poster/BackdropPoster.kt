package com.yeyint.movie_collection_compose.view.movie_detail_screen.back_drop_poster

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.yeyint.movie_collection_compose.R
import com.yeyint.movie_collection_compose.helper.MovieConstant
import com.yeyint.movie_collection_compose.helper.MyColor
import com.yeyint.movie_collection_compose.navigationController.Screen


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BackDropPoster(
    backDrop: String?,
    onPosterClick: ()-> Unit,
    onBackClick: ()-> Unit
){

    GlideImage(
        model = MovieConstant.baseBackdropPath + backDrop,
        contentDescription = "movie_backdrop",
        contentScale = ContentScale.Crop,
        loading = placeholder(R.mipmap.placeholder),
        failure = placeholder(R.mipmap.placeholder),
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .clickable { onPosterClick() }
    )

    Image(
        painter = painterResource(id = R.mipmap.back_arrow,),
        contentDescription = "back_arrow",
        modifier = Modifier
            .padding(20.dp)
            .background(
                color = MyColor.colorBlackTransparent,
                shape = RoundedCornerShape(30.dp)
            )
            .size(30.dp)
            .clickable { onBackClick() }
    )

}

@Preview
@Composable
fun BackDropPosterPreview() {

    BackDropPoster(
        backDrop = "",
        onPosterClick = {

        },
        onBackClick = {

        }
    )

}