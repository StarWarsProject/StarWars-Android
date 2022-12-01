package com.example.starwarsapp.ui.movieDetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.data.local.models.PlanetEntity
import com.example.starwarsapp.databinding.PlanetItemBinding

class PlanetAdapter(var planetList: List<PlanetEntity>) : RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder>() {
    class PlanetViewHolder(val binding: PlanetItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlanetItemBinding.inflate(inflater, parent, false)
        return PlanetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        holder.binding.planetItem = planetList[position]
    }

    override fun getItemCount(): Int {
        return planetList.size
    }

    fun updateList(planetEntityList: List<PlanetEntity>) {
        val oldSize = this.planetList.size
        this.planetList = planetEntityList
        if (planetEntityList.size <= oldSize) {
            notifyItemRangeRemoved(0, oldSize)
            notifyItemRangeInserted(0, planetEntityList.size)
        } else {
            notifyItemRangeChanged(0, this.planetList.size)
        }
    }
}
