package com.rachelmarotta.marvelapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rachelmarotta.marvelapp.domain.model.Character
import com.rachelmarotta.marvelapp.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.launch

class CharacterViewModel(private val getCharactersUseCase: GetCharactersUseCase) : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>().apply { value = emptyList() }
    val characters: LiveData<List<Character>> get() = _characters

    private val _isLoading = MutableLiveData<Boolean>()
    fun isLoading(): Boolean = _isLoading.value ?: false

    fun fetchCharacters(offset: Int, limit: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val newCharacters = getCharactersUseCase.invoke(offset, limit)
                val updatedCharacters = _characters.value.orEmpty() + newCharacters
                _characters.value = updatedCharacters
            } catch (e: Exception) {
                // Handle the error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchCharactersByName(name: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val characters = getCharactersUseCase.searchByName(name)
                _characters.value = characters
            } catch (e: Exception) {
                // Handle the error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(character: Character) {
        val updatedCharacters = _characters.value?.map {
            if (it.id == character.id) it.copy(isFavorite = !it.isFavorite) else it
        }
        _characters.value = updatedCharacters
    }
}
