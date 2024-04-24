package com.example.weather.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weather.convertDateFormat
import com.example.weather.data.DataOrException
import com.example.weather.model.Favorites
import com.example.weather.model.Weather
import com.example.weather.navigation.WeatherScreens
import com.example.weather.widgets.DayImageUrl
import com.example.weather.widgets.HumidityWindPressureRow
import com.example.weather.widgets.SunSetAndRise
import com.example.weather.widgets.WeatherAppBar
import com.example.weather.widgets.WeatherStateImage
import com.example.weather.widgets.WeekDaysForecast
import java.lang.Exception

@Composable
fun WeatherMainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    country : String,
    city : String
){
    val weatherData =
        produceState<DataOrException<Weather,Boolean,Exception>>(
            initialValue = DataOrException(loading = true)
        ){
            value = mainViewModel.getWeatherData(
                city = city,
                country = country
            )
        }.value

    Log.d("w","${weatherData.data}")
    if (weatherData.loading == true){
        CircularProgressIndicator()
    }else if (weatherData.data != null) {
        Log.d("GET" , weatherData.data!!.data[0].toString())
        MainScaffold(
            weather = weatherData.data!!,
            navController= navController,
            mainViewModel = mainViewModel,
            city = city,
            country = country
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    weather: Weather,
    navController: NavController,
    mainViewModel: MainViewModel,
    city: String,
    country: String
){
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city_name + "," + weather.country_code,
                icon = Icons.Default.FavoriteBorder   ,
                navController = navController,
                mainViewModel = mainViewModel,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            ) {
                mainViewModel.addFavorite(
                    Favorites(
                        country = country,
                        city = city
                    )
                )
            }
        }
    ) {
        MainContent(
            it = it,
            data = weather
        )
    }
}

@Composable
fun MainContent(
    it : PaddingValues,
    data: Weather
){
    Column(
        modifier = Modifier
            .padding(it)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = convertDateFormat(data.data[0].datetime),
             style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFA200)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = DayImageUrl(data = data.data[0]))
                Text(
                    text = data.data[0].temp.toString() + "º",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = data.data[0].weather.description,
                    fontStyle = FontStyle.Italic
                )
            }
        }

        HumidityWindPressureRow(weather = data)
        Divider()
        SunSetAndRise(weather = data)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "This Week",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
            )
        }

        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            shape = RoundedCornerShape(10.dp),
            color = Color.LightGray) {
            Spacer(modifier = Modifier.height(3.dp))
            LazyColumn{
                items(count = 7){
                    Spacer(modifier = Modifier.height(3.dp))
                    WeekDaysForecast(data.data[it+1])
                }
            }
        }
    }
}


