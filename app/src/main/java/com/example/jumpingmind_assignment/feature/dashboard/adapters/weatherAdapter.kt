package com.example.jumpingmind_assignment.feature.dashboard.adapters


import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.jumpingmind_assignment.R
import com.example.jumpingmind_assignment.databinding.WeatherItemBinding
import com.example.jumpingmind_assignment.feature.dashboard.data.ListItem

class WeatherAdapter(
    var context: Context,
    var mData: List<ListItem>,
    val callback: (item: ListItem) -> Unit
) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {


    lateinit var binding: WeatherItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: ListItem = mData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder(
        private val binding: WeatherItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItem) {
            binding.data = item
            binding.root.setOnClickListener {
                callback.invoke(item)
            }
            binding.image.setImageDrawable(ContextCompat.getDrawable(context, item.getIcon()))
        }
    }

}
