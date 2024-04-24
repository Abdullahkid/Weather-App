package com.example.weather.network

import com.example.weather.model.Weather
import com.example.weather.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "forecast/daily" )
    suspend fun getWeather(
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("key") key : String = Constants.API_KEY
    ): Weather
}