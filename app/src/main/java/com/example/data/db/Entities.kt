package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_presets")
data class SavedPresetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val brand: String,
    val model: String,
    val playstyleKey: String,
    val general: Int,
    val redDot: Int,
    val scope2x: Int,
    val scope4x: Int,
    val sniper: Int,
    val freeLook: Int,
    val dpi: Int,
    val fireButtonSize: Int,
    val timestamp: Long = System.currentTimeMillis()
)
