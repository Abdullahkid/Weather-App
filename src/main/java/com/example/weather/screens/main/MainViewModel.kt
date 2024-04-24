package com.example.weather.screens.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.DataOrException
import com.example.weather.model.Favorites
import com.example.weather.model.Weather
import com.example.weather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository)
    :ViewModel(){
        suspend fun getWeatherData(
            city: String,
            country: String
        ):DataOrException<Weather,Boolean,Exception>{
            return repository.getWeather(
                cityQuery = city,
                countryQuery = country
            )
        }
    var showMenu by  mutableStateOf(false)
    var favoriteBorder by mutableStateOf(false)

    private val _favoriteList = MutableStateFlow<List<Favorites>>(emptyList())
    val favoritesList = _favoriteList.asStateFlow()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavorite().distinctUntilChanged().collect{
                listOfFavorites ->
                if (listOfFavorites.isNullOrEmpty()){
                    Log.d("Empty", "Empty List")
                }else{
                    _favoriteList.value = listOfFavorites
                    Log.d("like","${favoritesList.value}")
                }
            }
        }
    }

    fun addFavorite(favorites: Favorites) = viewModelScope.launch{ repository.addFavorite(favorites) }
    fun deleteFavorite(favorites: Favorites) = viewModelScope.launch{ repository.deleteFavorite(favorites) }
    fun showFavorite() = viewModelScope.launch{ repository.getAllFavorite() }
}