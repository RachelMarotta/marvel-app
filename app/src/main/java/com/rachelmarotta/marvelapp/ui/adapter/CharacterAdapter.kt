package com.rachelmarotta.marvelapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rachelmarotta.marvelapp.databinding.ItemCharacterBinding
import com.rachelmarotta.marvelapp.domain.model.Character

class CharacterAdapter(private val onFavoriteClick: (Character) -> Unit) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val VIEW_TYPE_CHARACTER = 0
    private val VIEW_TYPE_LOADING = 1

    private val characters = mutableListOf<Character?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        if (character != null) {
            holder.bind(character)
        }
    }

    override fun getItemCount(): Int = characters.size

    override fun getItemViewType(position: Int): Int {
        return if (characters[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_CHARACTER
    }

    fun submitList(newCharacters: List<Character>, isLoading: Boolean) {
        val currentSize = characters.size
        if (isLoading) {
            characters.add(null) // Adiciona um item null para o indicador de carregamento
            notifyItemInserted(currentSize)
        } else {
            if (currentSize > 0 && characters[currentSize - 1] == null) {
                characters.removeAt(currentSize - 1) // Remove o item de carregamento
                notifyItemRemoved(currentSize - 1)
            }
            val newSize = characters.size + newCharacters.size
            characters.addAll(newCharacters)
            notifyItemRangeInserted(currentSize, newSize)
        }
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            binding.textViewName.text = character.name
            Glide.with(binding.textViewName.context)
                .load(character.thumbnailUrl)
                .into(binding.imageViewThumbnail)

            binding.btnFavorite.setOnClickListener {
                onFavoriteClick(character)
            }
        }
    }

//    inner class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)
}