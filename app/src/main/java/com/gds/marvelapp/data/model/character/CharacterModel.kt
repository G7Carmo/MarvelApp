package com.gds.marvelapp.data.model.character

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gds.marvelapp.data.model.Thumbmail
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity(tableName = "CharcterModel")
data class CharacterModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbmail
): Serializable
