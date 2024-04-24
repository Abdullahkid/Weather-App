package com.example.weather.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weather.R
import com.example.weather.convertDayFormat
import com.example.weather.formatDateTime
import com.example.weather.model.Data
import com.example.weather.model.Weather

fun DayImageUrl(data: Data): String {
    return "https://cdn.weatherbit.io/static/img/icons/${data.weather.icon}.png"
}

@Composable
fun WeekDaysForecast(data: Data) {
    Surface(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier
                .padding(4.dp)
                .width(80.dp)
                .height(65.dp)) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = convertDayFormat(data.valid_date),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${data.max_temp}º/${data.min_temp}º",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Image(painter = rememberAsyncImagePainter(DayImageUrl(data= data)),
                contentDescription = "icon image",
                modifier = Modifier.size(60.dp))
            Column(modifier = Modifier
                .width(100.dp)
                .height(65.dp)) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = data.weather.description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
fun SunSetAndRise(weather: Weather) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = formatDateTime(weather.data[0].sunrise_ts),
                style = MaterialTheme.typography.labelSmall
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunset_icon),
                contentDescription = "sunset icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = formatDateTime(weather.data[0].sunset_ts) ,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: Weather) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pngwing_com),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "${weather.data[0].rh}%",
                style = MaterialTheme.typography.labelSmall
            )
        }
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "${weather.data[0].pres}psi",
                style = MaterialTheme.typography.labelSmall
            )
        }
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.wind_icon),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "${weather.data[0].wind_spd}mph",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "icon image",
        modifier = Modifier.size(80.dp))
}
