package com.example.starwarsapp.ui.movieDetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.data.local.models.SpecieEntity
import com.example.starwarsapp.databinding.SpecieItemBinding

class SpecieAdapter(var speciesList: List<SpecieEntity>) : RecyclerView.Adapter<SpecieAdapter.SpecieViewHolder>() {

    class SpecieViewHolder(val binding: SpecieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SpecieItemBinding.inflate(inflater, parent, false)
        val movieViewHolder = SpecieViewHolder(binding)
        return movieViewHolder
    }

    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
        holder.binding.specieItem = speciesList[position]
    }

    override fun getItemCount(): Int {
        return speciesList.size
    }

    fun updateList(specieEntityList: List<SpecieEntity>) {
        val oldSize = this.speciesList.size
        this.speciesList = specieEntityList
        if (specieEntityList.size <= oldSize) {
            notifyItemRangeRemoved(0, oldSize)
            notifyItemRangeInserted(0, specieEntityList.size)
        } else {
            notifyItemRangeChanged(0, this.speciesList.size)
        }
    }
}
