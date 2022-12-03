package com.example.starwarsapp.ui.movieDetail.species.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.data.local.models.SpecieEntity
import com.example.starwarsapp.databinding.SpecieItemBinding

class SpecieAdapter(var speciesList: MutableList<SpecieEntity>, val listener: ISpecieListener) : RecyclerView.Adapter<SpecieAdapter.SpecieViewHolder>() {

    interface ISpecieListener {
        fun onSpecieTap(specie: SpecieEntity)
    }

    class SpecieViewHolder(val binding: SpecieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SpecieItemBinding.inflate(inflater, parent, false)
        val movieViewHolder = SpecieViewHolder(binding)
        return movieViewHolder
    }

    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return speciesList.size
    }
}
