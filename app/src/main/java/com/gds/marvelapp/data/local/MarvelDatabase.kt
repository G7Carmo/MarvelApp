package com.gds.marvelapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gds.marvelapp.data.model.character.CharacterModel

@Database(entities = [CharacterModel::class], version = 1, exportSchema = false)
@TypeConverters(Converts::class)
abstract class MarvelDatabase : RoomDatabase(){
    abstract fun marvelDao(): MarvelDao
}