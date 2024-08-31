package com.yeyint.movie_collection_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.yeyint.movie_collection_compose.navigationController.MovieAppNavigation
import com.yeyint.movie_collection_compose.ui.theme.Movie_collection_composeTheme
import com.yeyint.movie_collection_compose.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.showSplash.value
        }

        setContent {
            Movie_collection_composeTheme {

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {

                    MovieAppNavigation()
                }
            }
        }
    }
}