package com.yeyint.movie_collection_compose.view.home_screen.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yeyint.movie_collection_compose.R
import com.yeyint.movie_collection_compose.helper.MyColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String = "Movie Database") {
    TopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MyColor.colorPrimary
        ),
        title = {
            Row {
                Image(
                    painter = painterResource(id = R.mipmap.app_icon),
                    contentDescription = "app_bar_logo",
                    modifier = Modifier.width(25.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(text = title, color = colorResource(id = R.color.white))

                Spacer(modifier = Modifier.weight(1f))

                Text(text = "Powered by...", color = colorResource(id = R.color.white), fontSize = 10.sp)

                Image(
                    painter = painterResource(R.mipmap.compose),
                    contentDescription = "app_bar_logo",
                    modifier = Modifier.width(25.dp)
                )

                Spacer(modifier = Modifier.padding(end = 10.dp))
            }
        }
    )
}

@Preview
@Composable
fun AppBarPreview(modifier: Modifier = Modifier) {
    AppBar()
}