package com.rachelmarotta.marvelapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rachelmarotta.marvelapp.domain.usecase.GetCharactersUseCase

class CharacterViewModelFactory(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(getCharactersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}