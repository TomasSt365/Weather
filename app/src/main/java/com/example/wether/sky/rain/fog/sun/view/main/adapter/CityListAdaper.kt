package com.example.wether.sky.rain.fog.sun.view.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.wether.sky.rain.fog.sun.R
import com.example.wether.sky.rain.fog.sun.data.Weather

class CityListAdapter : RecyclerView.Adapter<CityListAdapter.MainFragmentViewHolder>() {

    private var weatherData: List<Weather> = listOf()
    private lateinit var listener: OnItemViewClickListener

    /*private var _biding: ItemListElementBinding? = null
    private val binding: ItemListElementBinding
        get() = _biding!!*/

    @SuppressLint("NotifyDataSetChanged")
    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    fun setOnItemViewClickListener(onItemViewClickListener: OnItemViewClickListener) {
        listener = onItemViewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        return MainFragmentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_element, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        holder.cityName.text = weatherData[position].city?.name ?: ""
    }

    override fun getItemCount() = weatherData.size

    inner class MainFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view),
        OnItemViewClickListener,
        View.OnClickListener {
        private val cardView: CardView = view.findViewById(R.id.itemContainer)
        val cityName: TextView = view.findViewById(R.id.cityName)

        override fun onItemClick(weather: Weather) {
            listener.onItemClick(weather)
        }

        override fun onClick(view: View?) {
            if (view != null) {
                when (view.id) {
                    R.id.itemContainer -> {
                        onItemClick(weather = weatherData[layoutPosition])//getPosition is Deprecated
                    }
                }
            }
        }

        init {
            cardView.setOnClickListener(this)
        }

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        //_biding = null
    }
}