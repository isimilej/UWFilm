package com.android.play.uwfilm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.play.uwfilm.data.UWFilmApplication
import com.android.play.uwfilm.data.movie.Movies
import com.android.play.uwfilm.data.movie.entity.Movie
import com.android.play.uwfilm.main.MainActivity
import com.android.play.uwfilm.widget.toast
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            Movies().fetchDailyBoxOfficeList().onSuccess { boxOfficeList ->
                UWFilmApplication.getInstance().boxOfficeList = boxOfficeList

                var movieList = mutableListOf<Movie>()
                boxOfficeList.forEach { it
                    movieList.add(Movie(
                        kobisMovieCode = it.kobisMovieCode,
                        title = it.title,
                        overview = it.overview,
                        poster = it.poster,
                        openDate = it.openDate,
                    ))
                }
                UWFilmApplication.getInstance().movieList = movieList

                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }.onFailure { e ->
                toast(e)
            }
        }
    }
}