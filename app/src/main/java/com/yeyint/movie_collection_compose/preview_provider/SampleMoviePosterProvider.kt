package com.yeyint.movie_collection_compose.preview_provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.yeyint.movie_collection.model.MoviePosterModel

class SampleMoviePosterProvider: PreviewParameterProvider<MoviePosterModel> {
    override val values = sequenceOf(
        MoviePosterModel(
            id = 0,
            movieId = 1,
            aspect_ratio = 0.6666,
            file_path = "/bab0zLK8y6jUkTcfMN3X9H3LTD4.jpg",
            height = 1280,
            iso_639_1 = "eb",
            vote_average = 5.246,
            vote_count = 2,
            width = 853
        )
    )
    override val count: Int = values.count()
}