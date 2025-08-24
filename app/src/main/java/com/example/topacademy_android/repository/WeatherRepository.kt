package com.example.topacademy_android.repository
import android.util.Log
import com.example.topacademy_android.models.*
import com.example.topacademy_android.network.RetrofitClient

class WeatherRepository {

    suspend fun getForecast(lon: Double, lat: Double): List<SevenTimerDay> {
        val resp = RetrofitClient.api.getCivilLight(lat = lat, lon = lon)

        val list = resp.dataseries.take(7).map { data ->
            val tMax = data.temp2m.max?.toIntOrNull() ?: 0
            val tMin = data.temp2m.min?.toIntOrNull() ?: 0
            val wind = data.wind10mMax?.toIntOrNull() ?: 0

            SevenTimerDay(
                date = data.date,
                weather = data.weather,
                temp2m = Temp2m(max = tMax, min = tMin),
                wind10m_max = wind
            )
        }

        Log.i("Weather", "Получено дней: ${list.size}")
        return list
    }
}