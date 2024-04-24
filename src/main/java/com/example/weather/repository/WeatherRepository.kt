package com.example.weather.repository

import android.util.Log
import com.example.weather.data.DataOrException
import com.example.weather.data.FavoriteDatabaseDao
import com.example.weather.model.Favorites
import com.example.weather.model.Weather
import com.example.weather.network.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api : WeatherApi ,
    private val favoriteDatabaseDao: FavoriteDatabaseDao
    ) {
    suspend fun getWeather(
        cityQuery: String,
        countryQuery: String
    ):DataOrException<Weather,Boolean,Exception> {
        val response = try {
            api.getWeather(
                city = cityQuery,
                country = countryQuery
            )
        }catch (e: Exception){
            Log.d("REX","getWeather : $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE","getWeather : $response")
        return DataOrException(data = response)
    }
    suspend fun addFavorite(favorites: Favorites) = favoriteDatabaseDao.insert(favorites)
    suspend fun deleteFavorite(favorites: Favorites) = favoriteDatabaseDao.deleteTask(favorites)
    fun getAllFavorite(): Flow<List<Favorites>> =
        favoriteDatabaseDao
            .getFavourites()
            .flowOn(Dispatchers.IO)
            .conflate()
}