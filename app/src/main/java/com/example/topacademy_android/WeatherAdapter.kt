package com.example.topacademy_android.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.topacademy_android.R
import com.example.topacademy_android.databinding.ItemWeatherBinding
import com.example.topacademy_android.models.SevenTimerDay
import com.google.android.material.card.MaterialCardView

class WeatherAdapter(
    private val onClick: (SevenTimerDay) -> Unit
) : ListAdapter<SevenTimerDay, WeatherAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<SevenTimerDay>() {
        override fun areItemsTheSame(oldItem: SevenTimerDay, newItem: SevenTimerDay) =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: SevenTimerDay, newItem: SevenTimerDay) =
            oldItem == newItem
    }

    inner class VH(val b: ItemWeatherBinding) : RecyclerView.ViewHolder(b.root) {
        init {
            b.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) onClick(getItem(pos))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        val ctx = holder.itemView.context

        // Дата
        holder.b.dateText.text = WeatherUi.formatDate(ctx, item.date)

        // Температура: "max° / min°"
        holder.b.tempText.text = ctx.getString(R.string.temp_format, item.temp2m.max, item.temp2m.min)

        holder.b.icon.setImageResource(WeatherUi.iconFor(item.weather))

        // Цвет карточки по температуре
        val colorRes = WeatherUi.tempColorRes(item.temp2m.max)
        (holder.b.root as MaterialCardView)
            .setCardBackgroundColor(ContextCompat.getColor(ctx, colorRes))
    }
}