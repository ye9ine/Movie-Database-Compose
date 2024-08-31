package com.yeyint.movie_collection_compose.preview_provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.yeyint.movie_collection.model.MovieModel
import com.yeyint.movie_collection.model.PreviewMovieAndPosterModel

class PreviewDataProvider: PreviewParameterProvider<PreviewMovieAndPosterModel> {
    override val values = sequenceOf(
        PreviewMovieAndPosterModel(
            id = 1,
            adult = false,
            backdropPath = "/pWsD91G2R1Da3AKM3ymr3UoIfRb.jpg",
            originalLanguage = null,
            originalTitle = "Badland Hunters",
            originalName = "Name",
            popularity = 1.0,
            posterPath = "/pWsD91G2R1Da3AKM3ymr3UoIfRb.jpg",
            releaseDate = "2024-01-26",
            title = "Badland Hunters",
            voteAverage = 6.3,
            voteCount = 0,
            video = false,
            firstAirDate = "2024-01-26",
            overview = "After a deadly earthquake turns Seoul into a lawless badland, a fearless huntsman springs into action to rescue a teenager abducted by a mad doctor.",

            movieId = 1,
            aspect_ratio = 0.6666,
            file_path = "/bab0zLK8y6jUkTcfMN3X9H3LTD4.jpg",
            height = 1280,
            iso_639_1 = "eb",
            vote_average = 5.246,
            vote_count = 2,
            width = 853
        ),
    )
    override val count: Int = values.count()
}