package com.android.play.uwfilm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.play.uwfilm.data.movie.Movies
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
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }.onFailure { e ->
                toast(e)
            }
        }
    }
}