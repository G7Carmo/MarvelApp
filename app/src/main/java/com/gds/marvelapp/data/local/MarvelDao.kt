package com.gds.marvelapp.data.local

import androidx.room.*
import com.gds.marvelapp.data.model.character.CharacterModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterModel: CharacterModel)

    @Query("SELECT * FROM CharcterModel")
    fun getAll() : Flow<List<CharacterModel>>

    @Delete
    suspend fun delete(characterModel: CharacterModel)
}