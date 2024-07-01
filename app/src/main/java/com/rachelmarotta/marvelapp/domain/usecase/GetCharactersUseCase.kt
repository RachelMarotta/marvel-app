package com.rachelmarotta.marvelapp.domain.usecase

import com.rachelmarotta.marvelapp.domain.model.Character
import com.rachelmarotta.marvelapp.domain.repository.CharacterRepository

class GetCharactersUseCase(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke(limit: Int, offset: Int): List<Character> {
        return characterRepository.getCharacters(limit, offset)
    }
}