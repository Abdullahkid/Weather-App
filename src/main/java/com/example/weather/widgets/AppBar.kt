package com.example.weather.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weather.model.Favorites
import com.example.weather.navigation.WeatherScreens
import com.example.weather.screens.main.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    mainViewModel: MainViewModel,
    isMainScreen: Boolean = true,
    navController: NavController,
    onAddActionClicked: ()-> Unit = {},
    onButtonClicked: () -> Unit = {}
){
    val items = listOf("Favourites")


    if (mainViewModel.showMenu){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopEnd)
                .absolutePadding(top = 45.dp, right = 20.dp),
        ) {
            DropdownMenu(
                modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)),
                expanded = mainViewModel.showMenu,
                onDismissRequest = { mainViewModel.showMenu = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            navController.navigate(WeatherScreens.FavouriteScreen.name)
                            mainViewModel.showMenu = false
                        },
                        modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)),
                        trailingIcon = { FavoriteIcon() }
                    )
                }
            }
        }
    }

    TopAppBar(
        title =
        {
            Text(
                text = title,
                color = Color.Black,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
            )
        },
        modifier = Modifier.shadow(20.dp),
        actions = {
            if (isMainScreen){
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
                IconButton(
                    onClick = { mainViewModel.showMenu = true }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "More Icon"
                    )
                }
            }else{
                Box{}
            }
        },
        navigationIcon = {
            if (icon!= null){
                IconButton(
                    onClick = { onLeftIconClicked(onButtonClicked,mainViewModel) }
                ) {
                    if ((icon == Icons.Default.FavoriteBorder) || ((icon == Icons.Default.Favorite))){
                        Icon(
                            imageVector =if (!mainViewModel.favoriteBorder)icon
                                    else Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            //tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.padding(5.dp),
                            tint = Color(0xFFFF2067)
                        )
                    }
                    else{
                        Icon(
                            imageVector = icon ,
                            contentDescription = "navigationIcon"
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun FavoriteIcon(){
    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "BorderHeart")
}

fun onLeftIconClicked(onButtonClicked: () -> Unit,mainViewModel: MainViewModel){
    onButtonClicked.invoke()
    mainViewModel.favoriteBorder = !mainViewModel.favoriteBorder
}

