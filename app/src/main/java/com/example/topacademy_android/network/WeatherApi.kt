package com.example.topacademy_android.network
import com.example.topacademy_android.models.SevenTimerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("bin/api.pl")
    suspend fun getCivilLight(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("product") product: String = "civillight",
        @Query("output") output: String = "json"
    ): SevenTimerResponse
}