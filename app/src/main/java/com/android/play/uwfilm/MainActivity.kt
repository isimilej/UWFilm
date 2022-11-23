package com.android.play.uwfilm

import android.os.Bundle
import android.widget.Toast
import com.android.play.uwfilm.data.Films

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var movies: ArrayList<String> = Films().fetch()
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
    }
}