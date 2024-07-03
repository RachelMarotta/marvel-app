package com.rachelmarotta.marvelapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rachelmarotta.marvelapp.R
import com.rachelmarotta.marvelapp.databinding.ItemCharacterBinding
import com.rachelmarotta.marvelapp.databinding.ItemLoadingBinding
import com.rachelmarotta.marvelapp.domain.model.Character
import com.rachelmarotta.marvelapp.ui.viewmodel.CharacterViewModel

class CharacterAdapter(
    private val viewModel: CharacterViewModel,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val characters = mutableListOf<Character?>()
    private var isLoadingAdded = false

    companion object {
        private const val ITEM_TYPE_CHARACTER = 0
        private const val ITEM_TYPE_LOADING = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (characters[position] == null) ITEM_TYPE_LOADING else ITEM_TYPE_CHARACTER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_CHARACTER) {
            val binding =
                ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CharacterViewHolder(binding, viewModel)
        } else {
            val binding =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_TYPE_CHARACTER) {
            (holder as CharacterViewHolder).bind(characters[position]!!)
        }
    }

    override fun getItemCount(): Int = characters.size

    fun submitList(newCharacters: List<Character>, isLoading: Boolean) {
        characters.clear()
        characters.addAll(newCharacters)
        if (isLoading) {
            addLoadingFooter()
        } else {
            removeLoadingFooter()
        }
        notifyDataSetChanged()
    }

    fun addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true
            characters.add(null)
            notifyItemInserted(characters.size - 1)
        }
    }

    fun removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false
            val position = characters.size - 1
            if (position >= 0 && characters[position] == null) {
                characters.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    inner class CharacterViewHolder(
        private val binding: ItemCharacterBinding,
        private val viewModel: CharacterViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.textViewName.text = character.name
            Glide.with(binding.root.context)
                .load(character.thumbnailUrl)
                .into(binding.imageViewThumbnail)

            val favoriteButton = binding.btnFavorite
            favoriteButton.setImageResource(
                if (character.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
            favoriteButton.setOnClickListener {
                viewModel.toggleFavorite(character)
            }
        }
    }

    inner class LoadingViewHolder(binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)
}