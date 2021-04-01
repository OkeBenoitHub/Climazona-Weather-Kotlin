package com.www.climazona.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Weather Api Service
 */

private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"

/**
 * Weather units options
 */
enum class WeatherApiUnits(val value: String) {
    FAHRENHEIT("imperial"),
    CELSIUS("metric"),
    KELVIN("standard")
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(WEATHER_BASE_URL)
    .build()

/**
 * A public interface that exposes the getWeatherData methods
 */
interface WeatherApiService {
    /**
     * Returns a Coroutine which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "weather" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("weather")
    suspend fun getWeatherDataByCityName(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") appid: String
    ): WeatherProperty

    @GET("weather")
    suspend fun getWeatherDataByLatLong(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") appid: String
    ): WeatherProperty

    @GET("weather")
    suspend fun getWeatherDataByZipCode(
        @Query("zip") zip: Int,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") appid: String
    ): WeatherProperty
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object WeatherApi {
    val retrofitService : WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}