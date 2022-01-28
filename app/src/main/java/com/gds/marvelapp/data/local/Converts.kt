package com.gds.marvelapp.data.local

import androidx.room.TypeConverter
import com.gds.marvelapp.data.model.Thumbmail
import com.google.gson.Gson

class Converts {
    @TypeConverter
    fun fromThumbnaim(thumbnail : Thumbmail): String = Gson().toJson(thumbnail)
    @TypeConverter
    fun toThumbnail(thumbnail: String) : Thumbmail = Gson().fromJson(thumbnail,Thumbmail::class.java)
}