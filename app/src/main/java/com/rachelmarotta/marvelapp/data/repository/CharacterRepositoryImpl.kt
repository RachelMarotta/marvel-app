package com.rachelmarotta.marvelapp.data.repository

import com.rachelmarotta.marvelapp.BuildConfig
import com.rachelmarotta.marvelapp.data.remote.MarvelService
import com.rachelmarotta.marvelapp.data.remote.utils.generateMd5Hash
import com.rachelmarotta.marvelapp.domain.model.Character
import com.rachelmarotta.marvelapp.domain.repository.CharacterRepository
import com.rachelmarotta.marvelapp.data.model.toDomain

class CharacterRepositoryImpl(private val apiService: MarvelService) : CharacterRepository {

    override suspend fun getCharacters(limit: Int, offset: Int): List<Character> {
        val publicKey = BuildConfig.MARVEL_PUBLIC_KEY
        val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        val ts = System.currentTimeMillis().toString()
        val hash = generateMd5Hash("$ts$privateKey$publicKey")

        val response = apiService.getCharacters(ts, publicKey, hash, limit, offset)
        return if (response.isSuccessful) {
            response.body()?.data?.results?.map { it.toDomain() } ?: emptyList()
        } else {
            throw Exception("Error fetching characters")
        }
    }
}
