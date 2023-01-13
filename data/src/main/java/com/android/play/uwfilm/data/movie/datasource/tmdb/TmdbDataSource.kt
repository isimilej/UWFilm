package com.android.play.uwfilm.data.movie.datasource.tmdb

import com.android.play.uwfilm.data.movie.MovieDataSource
import com.android.play.uwfilm.data.movie.Trailer
import com.android.play.uwfilm.data.movie.entity.BoxOffice
import com.android.play.uwfilm.data.movie.entity.Movie
import com.android.play.uwfilm.data.movie.entity.SearchResult
import com.android.play.uwfilm.data.movie.entity.StillCut

class TmdbDataSource: MovieDataSource {

    private val service: TmdbServiceApi by lazy {
        TmdbDataSourceProvider.provideApi()
    }

    override suspend fun fetchDailyBoxOfficeList(): Result<List<BoxOffice>> {
        TODO("not supported operation in TmdbDataSource")
    }

    override suspend fun fetchMovieInformation(movieCode: String): Result<List<StillCut>> {
        TODO("not supported operation in TmdbDataSource")
    }

    override suspend fun fetchComingSoonList(): List<BoxOffice> {
        TODO("not supported operation in TmdbDataSource")
    }

    override suspend fun fetchDetail(movieCode: String) {
        //https://image.tmdb.org/t/p/w500/hZkgoQYus5vegHoetLkCJzb17zJ.jpg
        service.fetchDetail(movieCode)
    }

    override suspend fun search(movieName: String): Result<List<SearchResult>> {
        var result = service.search(movieName).onSuccess { response ->
            val searchResultList = mutableListOf<SearchResult>()
            response.resultList.forEach { result ->
                searchResultList.add(SearchResult(id = result.id, title = result.title, openDate = result.releaseDate))
            }
            return Result.success(searchResultList)
        }
        return Result.failure(result.exceptionOrNull() ?: IllegalStateException("Unknown Exception"))
    }

    override suspend fun getTrailerVideos(movieId: String): Result<List<Trailer>> {
        val result = service.getVideos(movieId).onSuccess { response ->
            val trailerList = mutableListOf<Trailer>()
            response.videoList.forEach { video ->
                if ("youtube".equals(video.site, true) and "trailer".equals(video.type, true)) {
                    trailerList.add(Trailer(key = video.key, title = video.name))
                }
            }
            return Result.success(trailerList)
        }
        return Result.failure(result.exceptionOrNull() ?: IllegalStateException("Unknown Exception"))
    }

}