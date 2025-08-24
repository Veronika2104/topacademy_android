package com.example.topacademy_android.ui
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topacademy_android.databinding.ActivityWeatherBinding
import com.example.topacademy_android.models.SevenTimerDay
import com.example.topacademy_android.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.topacademy_android.R

class WeatherActivity : AppCompatActivity() {

    private lateinit var b: ActivityWeatherBinding
    private val repo = WeatherRepository()
    private val adapter = WeatherAdapter { openDetails(it) }

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        android.util.Log.d("WeatherActivity", "onCreate start")
        enableEdgeToEdge()
        b = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(b.root)
        android.util.Log.d("WeatherActivity", "setContentView ok")

        setSupportActionBar(b.toolbar)
        android.util.Log.d("WeatherActivity", "toolbar ok")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        b.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        b.toolbar.subtitle = getString(R.string.city_country)

        b.weatherRecycler.layoutManager = LinearLayoutManager(this)
        b.weatherRecycler.adapter = adapter

        loadForecast()
    }

    private fun loadForecast() {
        b.progress.visibility = View.VISIBLE
        b.errorText.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val list = withContext(Dispatchers.IO) {

                    repo.getForecast(lon = 43.847, lat = 40.789)
                }


                val today = list.firstOrNull()
                if (today != null) {
                    b.headerDay.text = WeatherUi.formatDate(this@WeatherActivity, today.date)
                    b.headerCity.text = getString(R.string.city_country).uppercase()
                    b.headerTemp.text = getString(R.string.temp_single_format, today.temp2m.max)
                    b.headerIcon.setImageResource(WeatherUi.iconFor(today.weather))
                }

                adapter.submitList(list)
            } catch (e: Exception) {
                b.errorText.visibility = View.VISIBLE
            } finally {
                b.progress.visibility = View.GONE
            }
        }
    }


    private fun openDetails(day: SevenTimerDay) {
        val i = Intent(this, WeatherDetailActivity::class.java).apply {
            putExtra("date", day.date)
            putExtra("code", day.weather)
            putExtra("tmax", day.temp2m.max)
            putExtra("tmin", day.temp2m.min)
            putExtra("windmax", day.wind10m_max)

        }
        startActivity(i)
    }
}