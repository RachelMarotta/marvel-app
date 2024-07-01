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

    fun fetchCharacters(limit: Int, offset: Int) {
        viewModelScope.launch {
            try {
                val result = getCharactersUseCase(limit, offset)
                _characters.value = result
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}