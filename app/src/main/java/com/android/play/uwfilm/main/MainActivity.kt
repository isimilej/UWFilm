package com.android.play.uwfilm.main

import android.os.Bundle
import android.util.Log
import com.android.play.uwfilm.BaseActivity
import com.android.play.uwfilm.R
import com.android.play.uwfilm.data.movie.entity.BoxOffice

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}