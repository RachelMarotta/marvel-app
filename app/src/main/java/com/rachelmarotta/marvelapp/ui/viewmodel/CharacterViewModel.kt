package com.rachelmarotta.marvelapp.ui.viewmodel

import android.util.Log
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

    private val _allCharacters = MutableLiveData<List<Character>>().apply { value = emptyList() }
    val allCharacters: LiveData<List<Character>> get() = _allCharacters

    private val _isLoading = MutableLiveData<Boolean>()
    fun isLoading(): Boolean = _isLoading.value ?: false

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    fun fetchCharacters(offset: Int, limit: Int, restart: Boolean = false) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val newCharacters = getCharactersUseCase.invoke(offset, limit)
                val updatedCharacters =
                    if (restart) newCharacters else _characters.value.orEmpty() + newCharacters
                _characters.value = updatedCharacters
                _allCharacters.value = updatedCharacters
                _isEmpty.value = updatedCharacters.isEmpty()
            } catch (e: Exception) {
                Log.e("CharacterViewModel.fetchCharacters", "Exception fetching characters", e)
                throw e
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
                _isEmpty.value = characters.isEmpty()
            } catch (e: Exception) {
                Log.e(
                    "CharacterViewModel.searchCharactersByName",
                    "Exception fetching characters",
                    e
                )
                throw e
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun showAllCharacters() {
        _characters.value = _allCharacters.value
        _isEmpty.value = _allCharacters.value.isNullOrEmpty()
    }

    fun toggleFavorite(character: Character) {
        val updatedCharacters = _characters.value?.map {
            if (it.id == character.id) it.copy(isFavorite = !it.isFavorite) else it
        }
        _characters.value = updatedCharacters
    }
}
