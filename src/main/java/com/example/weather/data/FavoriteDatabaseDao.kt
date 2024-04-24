package com.example.weather.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather.model.Favorites

@Dao
interface FavoriteDatabaseDao {
    @Query("SELECT * from Favorites_tbl")
    fun getFavourites(): kotlinx.coroutines.flow.Flow<List<Favorites>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorites: Favorites)

    @Delete
    suspend fun deleteTask(favorites: Favorites)
}