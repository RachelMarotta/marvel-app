package com.rachelmarotta.marvelapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rachelmarotta.marvelapp.databinding.ItemCharacterBinding
import com.rachelmarotta.marvelapp.databinding.ItemLoadingBinding
import com.rachelmarotta.marvelapp.domain.model.Character

class CharacterAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_CHARACTER = 0
    private val VIEW_TYPE_LOADING = 1

    private val items = mutableListOf<Any>()

    fun submitList(characters: List<Character>, isLoading: Boolean) {
        items.clear()
        items.addAll(characters)
        if (isLoading) {
            items.add(Any()) // Add a loading item
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is Character) VIEW_TYPE_CHARACTER else VIEW_TYPE_LOADING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CHARACTER) {
            val binding =
                ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CharacterViewHolder(binding)
        } else {
            val binding =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CharacterViewHolder) {
            val character = items[position] as Character
            holder.bind(character)
        }
    }

    override fun getItemCount(): Int = items.size

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.textViewName.text = character.name
            Glide.with(binding.imageViewThumbnail.context)
                .load(character.thumbnailUrl)
                .into(binding.imageViewThumbnail)
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)
}