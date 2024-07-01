package com.rachelmarotta.marvelapp.domain.repository

import com.rachelmarotta.marvelapp.domain.model.Character

interface CharacterRepository {
    suspend fun getCharacters(limit: Int, offset: Int): List<Character>
}