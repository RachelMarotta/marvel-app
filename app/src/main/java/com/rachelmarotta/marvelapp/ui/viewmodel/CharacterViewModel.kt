package com.rachelmarotta.marvelapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rachelmarotta.marvelapp.domain.model.Character
import com.rachelmarotta.marvelapp.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.launch

class CharacterViewModel(private val getCharactersUseCase: GetCharactersUseCase) : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var currentOffset = 0
    private val limit = 20
    private var isLoading = false

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            try {
                val result = getCharactersUseCase(limit, currentOffset)
                val updatedCharacters = _characters.value.orEmpty() + result
                _characters.postValue(updatedCharacters)
                currentOffset += limit
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
            isLoading = false
        }
    }
}