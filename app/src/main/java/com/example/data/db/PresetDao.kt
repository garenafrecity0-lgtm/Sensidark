package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PresetDao {
    @Query("SELECT * FROM saved_presets ORDER BY timestamp DESC")
    fun getAllPresets(): Flow<List<SavedPresetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreset(preset: SavedPresetEntity): Long

    @Delete
    suspend fun deletePreset(preset: SavedPresetEntity)

    @Query("DELETE FROM saved_presets WHERE id = :id")
    suspend fun deleteById(id: Long)
}
