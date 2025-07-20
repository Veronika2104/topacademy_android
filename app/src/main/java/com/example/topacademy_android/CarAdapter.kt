package com.example.topacademy_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewCar: ImageView = itemView.findViewById(R.id.imageViewCar)
        val textViewBrandModel: TextView = itemView.findViewById(R.id.textViewBrandModel)
        val textViewYearCost: TextView = itemView.findViewById(R.id.textViewYearCost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.imageViewCar.setImageResource(car.imageResId)
        holder.textViewBrandModel.text = "${car.brand} ${car.model}"
        holder.textViewYearCost.text = "${car.year} год, ${car.cost} ₽"
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}