package com.example.topacademy_android
import com.example.topacademy_android.ui.HomeActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.topacademy_android.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import com.example.topacademy_android.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ON_CREATE = "ON_CREATE"
    }

    private lateinit var binding: ActivityMainBinding
    private var onCreateTime: Long = 0
    private var onStartTime: Long = 0
    private var onResumeTime: Long = 0
    private fun parseNumber(value: String): Int {
        val cleanValue = value.replace("%", "") // убираем %
        return cleanValue.toInt()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i(ON_CREATE, "Активити создана!")
        onCreateTime = System.currentTimeMillis()
        Log.i("MainActivity", "onCreate time = $onCreateTime")
        val button = binding.startButton

        button.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val latitude = 40.7894
        val longitude = 43.8475

        lifecycleScope.launch {
            try {
                val weather = RetrofitClient.api.getCivilLight(
                    lat = latitude,
                    lon = longitude

                )

                Log.i("Weather", "Прогноз получен для Гюмри")

                weather.dataseries.take(7).forEachIndexed { index, data ->
                    val humidityString = "${data.rh2m}%"
                    val humidity = parseNumber(humidityString)

                    Log.i(
                        "Weather",
                        "День $index: температура ${data.temp2m}°C, влажность $humidity%, облачность ${data.cloudcover}/9"
                    )
                }
            } catch (e: Exception) {
                Log.e("Weather", "Ошибка: ${e.message}")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("MainActivity", "Активити запущена!")
        onStartTime = System.currentTimeMillis()
        val diffOnStart = onStartTime - onCreateTime
        Log.i("MainActivity", "Прошло $diffOnStart мс между onCreate и onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "Активити возобновлена!")
        onResumeTime = System.currentTimeMillis()
        val diffOnResume = onResumeTime - onStartTime
        Log.i("MainActivity", "Прошло $diffOnResume мс между onStart и onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivity", "Активити приостановлена!")
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivity", "Активити остановлена!")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainActivity", "Активити перезапущена!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity", "Активити уничтожена!")
    }
}