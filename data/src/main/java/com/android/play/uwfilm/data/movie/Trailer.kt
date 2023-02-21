package com.android.play.uwfilm.data.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trailer(
    val key: String = "",
    val title: String = "",
) : Parcelable