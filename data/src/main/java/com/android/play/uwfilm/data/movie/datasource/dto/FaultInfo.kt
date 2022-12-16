package com.android.play.uwfilm.data.movie.datasource.dto

import kotlinx.serialization.Serializable

@Serializable
data class FaultInfo(
    val message: String = "",
    val errorCode: String = ""
)