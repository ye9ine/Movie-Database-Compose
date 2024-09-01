package com.yeyint.movie_collection_compose.view.photo_detail_screen
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.yeyint.movie_collection_compose.R
import com.yeyint.movie_collection_compose.helper.MyColor
import com.yeyint.movie_collection_compose.shareViewModel.ShareViewModel
import soup.compose.photo.ExperimentalPhotoApi
import soup.compose.photo.PhotoBox



@Composable
fun PhotoDetailViewScreen(
    shareViewModel: ShareViewModel,
){

    PhotoDetailBody(
        image = shareViewModel.getPoster()!!,
    )
}


@OptIn(ExperimentalPhotoApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun PhotoDetailBody(
    image: String,
) {
    Scaffold { padding->
        Box(
            modifier = Modifier
                .background(color = MyColor.colorBlack)
                .padding(padding)
                .fillMaxHeight()
                .fillMaxWidth()
        ){

            PhotoBox(
                modifier = Modifier.align(alignment = Alignment.Center).fillMaxHeight().fillMaxWidth()
            ) {
                GlideImage(
                    model = image,
                    contentDescription = "image detail",
                    loading = placeholder(R.mipmap.placeholder),
                    failure = placeholder(R.mipmap.placeholder),
                    modifier = Modifier.align(alignment = Alignment.Center).fillMaxHeight().fillMaxWidth()
                )

            }

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
            )
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewPhotoDetail(modifier: Modifier = Modifier) {

    SharedTransitionLayout {
        PhotoDetailBody(
            image = "",
        )
    }


}