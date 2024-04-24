package com.example.weather.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.data.FavoriteDatabase
import com.example.weather.data.FavoriteDatabaseDao
import com.example.weather.network.WeatherApi
import com.example.weather.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideOpenWeatherApi():WeatherApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
    @Provides
    @Singleton
    fun provideFavoriteDao(favoriteDatabase: FavoriteDatabase):FavoriteDatabaseDao{
       return favoriteDatabase.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDatabase(@ApplicationContext context:Context):FavoriteDatabase =
        Room.databaseBuilder(
            context = context,
            klass = FavoriteDatabase::class.java,
            name = "Favorites_tbl"
        ).fallbackToDestructiveMigration().build()
}