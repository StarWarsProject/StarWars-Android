package com.example.starwarsapp.ui.movieDetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.data.local.models.CharacterInfoView
import com.example.starwarsapp.databinding.CharacterItemBinding

class CharacterAdapter(var characterList: List<CharacterInfoView>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CharacterItemBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.binding.characterItem = characterList[position]
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun updateList(characterEntityList: List<CharacterInfoView>) {
        val oldSize = this.characterList.size
        this.characterList = characterEntityList
        if (characterEntityList.size <= oldSize) {
            notifyItemRangeRemoved(0, oldSize)
            notifyItemRangeInserted(0, characterEntityList.size)
        } else {
            notifyItemRangeChanged(0, this.characterList.size)
        }
    }
}
