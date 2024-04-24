package com.example.weather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather.model.Favorites

@Database(entities = [Favorites::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase: RoomDatabase(){
    abstract fun favoriteDao():FavoriteDatabaseDao
}