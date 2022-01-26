package com.gds.marvelapp.data.model.character

import com.gds.marvelapp.data.model.Thumbmail
import java.io.Serializable

data class CharacterModel(
    val id : Int,
    val name : String,
    val description: String,
    val thumbmail: Thumbmail
): Serializable
