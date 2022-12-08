package com.example.starwarsapp.ui.movieDetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.data.local.models.StarshipEntity
import com.example.starwarsapp.databinding.ShipItemBinding

class StarshipAdapter(var starshipsList: List<StarshipEntity>) : RecyclerView.Adapter<StarshipAdapter.StarshipViewHolder>() {

    class StarshipViewHolder(val binding: ShipItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarshipViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShipItemBinding.inflate(inflater, parent, false)
        return StarshipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StarshipViewHolder, position: Int) {
        holder.binding.shipItem = starshipsList[position]
    }

    override fun getItemCount(): Int {
        return starshipsList.size
    }

    fun updateList(starshipEntityList: List<StarshipEntity>) {
        val oldSize = this.starshipsList.size
        this.starshipsList = starshipEntityList
        if (starshipEntityList.size <= oldSize) {
            notifyItemRangeRemoved(0, oldSize)
            notifyItemRangeInserted(0, starshipEntityList.size)
        } else {
            notifyItemRangeChanged(0, this.starshipsList.size)
        }
    }
}
