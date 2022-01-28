package com.gds.marvelapp.data.model

import com.google.gson.annotations.SerializedName

data class Thumbmail(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String
)