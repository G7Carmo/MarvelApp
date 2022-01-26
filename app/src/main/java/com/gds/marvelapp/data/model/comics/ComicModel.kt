package com.gds.marvelapp.data.model.comics

import com.gds.marvelapp.data.model.Thumbmail
import java.io.Serializable

data class ComicModel(
    val id : Int,
    val title: String,
    val description: String,
    val thumbmail: Thumbmail
):Serializable
