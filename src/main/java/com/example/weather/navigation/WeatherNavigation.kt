package com.example.weather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weather.screens.main.FavouriteScreen
import com.example.weather.screens.main.MainViewModel
import com.example.weather.screens.main.WeatherMainScreen
import com.example.weather.screens.search.SearchScreen
import com.example.weather.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation(){
    val navController = rememberNavController()
    val mainViewModel = hiltViewModel<MainViewModel>()
    val favoriteList= mainViewModel.favoritesList.collectAsState().value
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ){
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable(
            route = "$route/{city}/{country}",
            arguments= listOf(
                navArgument(name = "city"){
                    type = NavType.StringType
                },navArgument(name = "country"){
                    type = NavType.StringType
                }
            )
        ){ navBack->
            navBack.arguments?.getString("country").let {country->
                navBack.arguments?.getString("city").let {city->
                    WeatherMainScreen(
                        navController = navController,
                        mainViewModel = mainViewModel,
                        country = country!!,
                        city = city!!
                    )
                }
            }
        }
        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
        composable(WeatherScreens.FavouriteScreen.name){
            FavouriteScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                favoritesList = favoriteList
            )
        }
    }
}

