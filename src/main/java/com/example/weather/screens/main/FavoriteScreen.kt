package com.example.weather.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weather.model.Favorites
import com.example.weather.navigation.WeatherScreens
import com.example.weather.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(
    navController : NavController,
    mainViewModel: MainViewModel,
    favoritesList : List<Favorites>,
){
    var clickCount by remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Favourite",
                icon = Icons.Default.ArrowBack,
                navController = navController,
                mainViewModel = mainViewModel,
                onButtonClicked = { navController.navigateUp() }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .fillMaxSize(),
            color = Color.LightGray,
            shape = RoundedCornerShape(10.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                .padding(3.dp)
            ){
                items(favoritesList){
                    Surface(
                        modifier = Modifier
                            .padding(top = 2.dp, start = 2.dp, end = 2.dp)
                            .fillMaxWidth()
                            .height(70.dp),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    navController.navigate(WeatherScreens.MainScreen.name + "/${it.city}/${it.country}")
                                    clickCount++
                                    if (clickCount == 2) {
                                        onDoubleClick(mainViewModel, favorites = it)
                                        clickCount = 0
                                    }
                                },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start) {
                            Text(
                                text = " ${it.city} , ${it.country} ",
                                style = TextStyle(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp
                                ),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}

fun onDoubleClick(mainViewModel: MainViewModel,favorites: Favorites) {
    mainViewModel.deleteFavorite(favorites)
}

