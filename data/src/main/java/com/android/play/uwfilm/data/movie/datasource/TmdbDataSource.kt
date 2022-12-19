package com.android.play.uwfilm.data.movie.datasource

import com.android.play.uwfilm.data.movie.BoxOffice
import com.android.play.uwfilm.data.movie.Movie
import com.android.play.uwfilm.data.movie.MovieDataSource

class TmdbDataSource: MovieDataSource {

    private val service: TmdbServiceApi by lazy {
        TmdbDataSourceProvider.provideApi()
    }

    override suspend fun fetchDailyBoxOfficeList(date: String): Result<List<BoxOffice>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMovieInformation(movieCode: String): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun fetchComingSoonList(): List<BoxOffice> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchDetail(movieCode: String) {
        //https://image.tmdb.org/t/p/w500/hZkgoQYus5vegHoetLkCJzb17zJ.jpg
        service.fetchDetail(movieCode)
    }

    override suspend fun search(movieName: String) {
        //TODO("Not yet implemented")
        service.search(movieName)
    }

}