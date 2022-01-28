package com.gds.marvelapp.data.model.comics

import com.gds.marvelapp.data.model.Thumbmail
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicModel(
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbmail
):Serializable
