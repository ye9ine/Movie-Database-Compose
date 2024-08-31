package com.yeyint.movie_collection_compose.navigationController
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yeyint.movie_collection_compose.shareViewModel.ShareViewModel
import com.yeyint.movie_collection_compose.view.home_screen.HomeScreen
import com.yeyint.movie_collection_compose.view.movie_detail_screen.MovieDetailScreen
import com.yeyint.movie_collection_compose.view.photo_detail_screen.PhotoDetailViewScreen

enum class Screen {
    Home,
    MovieDetail,
    PhotoDetailView
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieAppNavigation() {

    SharedTransitionLayout{
        val navController = rememberNavController()
        val shareViewModel: ShareViewModel = viewModel()

        NavHost(navController = navController, startDestination = Screen.Home.name, builder = {
            composable(Screen.Home.name) {
                HomeScreen(
                    navController = navController,
                    shareViewModel = shareViewModel,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }

            composable(Screen.MovieDetail.name) {
                MovieDetailScreen(
                    navController = navController,
                    shareViewModel = shareViewModel,
                    lifecycleOwner = LocalLifecycleOwner.current,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }

            composable(Screen.PhotoDetailView.name) {
                PhotoDetailViewScreen(
                    shareViewModel = shareViewModel,
                )
            }
        })
    }



}