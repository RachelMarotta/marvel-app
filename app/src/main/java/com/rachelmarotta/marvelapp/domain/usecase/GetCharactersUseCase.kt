package com.rachelmarotta.marvelapp.domain.usecase

import com.rachelmarotta.marvelapp.domain.model.Character
import com.rachelmarotta.marvelapp.domain.repository.CharacterRepository

class GetCharactersUseCase(private val characterRepository: CharacterRepository) {
    suspend fun invoke(offset: Int, limit: Int): List<Character> {
        return characterRepository.getCharacters(offset, limit)
    }
}