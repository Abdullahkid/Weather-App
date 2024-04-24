package com.example.weather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "Favorites_tbl")
data class Favorites(
    @PrimaryKey
    val id : Int = Random.nextInt(),
    @ColumnInfo(name = "Country")
    val country: String,
    @ColumnInfo(name = "City")
    val city : String
)
