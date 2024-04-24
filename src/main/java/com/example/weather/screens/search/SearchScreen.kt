package com.example.weather.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weather.navigation.WeatherScreens
import com.example.weather.screens.main.MainViewModel
import com.example.weather.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    mainViewModel: MainViewModel
){
    var city by remember {
        mutableStateOf("")
    }
    var country by remember {
        mutableStateOf("")
    }
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Search",
            navController = navController,
            mainViewModel = mainViewModel,
            icon = Icons.Default.ArrowBack,
            isMainScreen = false
        ) {
            navController.navigateUp()
        }
    }) {

        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {

            val searchQueryState1 = rememberSaveable {
                mutableStateOf("")
            }
            val searchQueryState2 = rememberSaveable {
                mutableStateOf("")
            }

            val valid2 = remember(searchQueryState2.value){
                searchQueryState2.value.trim().isEmpty()
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

//                    onAction = KeyboardActions {
//                        if (!valid2)return@KeyboardActions
//                        city = searchQueryState1.value.trim()
//                        country = searchQueryState2.value.trim()
//                        searchQueryState1.value = ""
//                        searchQueryState2.value = ""
//                        keyboardController?.hide()
//                    }



                val keyboardController = LocalSoftwareKeyboardController.current

                OutlinedTextField(
                    value = searchQueryState1.value,
                    onValueChange = {value->
                        searchQueryState1.value = value
                    },
                    label = { Text(text = "City")},
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),

                    keyboardActions = KeyboardActions(
                        onNext = null
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Blue,
                        cursorColor = Color.Black
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                )

                OutlinedTextField(
                    value = searchQueryState2.value,
                    onValueChange = {value->
                        searchQueryState2.value = value
                    },
                    label = { Text(text = "Country")},
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),

                    keyboardActions = KeyboardActions(
                        onDone = {
                            city = searchQueryState1.value.trim()
                            country = searchQueryState2.value.trim()
                            searchQueryState1.value = ""
                            searchQueryState2.value = ""
                            keyboardController?.hide() // Hide the keyboard when "Done" is pressed
                            navController.navigate(WeatherScreens.MainScreen.name + "/$city/$country")
                        }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Blue,
                        cursorColor = Color.Black
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                )
            }
        }
    }
}


