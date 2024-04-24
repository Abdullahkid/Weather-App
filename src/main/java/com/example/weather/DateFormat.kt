package com.example.weather

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDateTime(timestamp : Int):String{
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = java.util.Date(timestamp.toLong()*1000)

    return sdf.format(date)
}

fun convertDateFormat(apiDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEE, MMM dd", Locale.getDefault())

    val date = inputFormat.parse(apiDate)
    return outputFormat.format(date!!)
}

fun convertDayFormat(apiDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEE", Locale.getDefault())

    val date = inputFormat.parse(apiDate)
    return outputFormat.format(date!!)
}