package com.android.play.uwfilm.data.movie.datasource.tmdb

import com.android.play.uwfilm.data.movie.MovieDataSource
import com.android.play.uwfilm.data.movie.Trailer
import com.android.play.uwfilm.data.movie.entity.BoxOffice
import com.android.play.uwfilm.data.movie.entity.Movie

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

    override suspend fun search(movieName: String): Result<Movie> {
        service.search(movieName).onSuccess {
            if (it.results.isNotEmpty()) {
                it.results.forEach { found ->
                    return Result.success(Movie(code = found.id, name = found.title, thumb = "", synopsis = ""))
                }
            } else {
                return Result.failure(IllegalStateException("Not found movie!"))
            }
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(IllegalStateException("Unknown Exception"))
    }

    override suspend fun fetchVideos(movieCode: String): Result<Trailer> {
//        MovieVideosResponse = service.video(movieCode)
        service.video(movieCode).onSuccess { response ->
            response.results.forEach { result ->
                return Result.success(Trailer(key = result.key))
            }
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(IllegalStateException("Unknown Exception"))
    }
}