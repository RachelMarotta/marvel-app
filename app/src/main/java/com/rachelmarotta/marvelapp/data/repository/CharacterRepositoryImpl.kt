package com.rachelmarotta.marvelapp.data.repository

import android.util.Log
import com.rachelmarotta.marvelapp.BuildConfig
import com.rachelmarotta.marvelapp.data.remote.MarvelService
import com.rachelmarotta.marvelapp.data.remote.utils.generateMd5Hash
import com.rachelmarotta.marvelapp.domain.model.Character
import com.rachelmarotta.marvelapp.domain.repository.CharacterRepository
import com.rachelmarotta.marvelapp.data.model.toDomain

class CharacterRepositoryImpl(private val marvelService: MarvelService) : CharacterRepository {
    private val publicKey = BuildConfig.MARVEL_PUBLIC_KEY
    private val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
    private val ts = System.currentTimeMillis().toString()
    private val hash = generateMd5Hash("$ts$privateKey$publicKey")

    override suspend fun getCharacters(offset: Int, limit: Int): List<Character> {
        try {
            val response = marvelService.getCharacters(ts, publicKey, hash, limit, offset)
            return if (response.isSuccessful) {
                response.body()?.data?.results?.map { it.toDomain() } ?: emptyList()
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("CharacterRepositoryImpl", "Error fetching characters: $errorBody")
                throw Exception("Error fetching characters: $errorBody")
            }
        } catch (e: Exception) {
            Log.e("CharacterRepositoryImpl", "Exception fetching characters", e)
            throw e
        }
    }

    override suspend fun searchCharactersByName(name: String): List<Character> {
        val response = marvelService.searchCharactersByName(ts, publicKey, hash, name)
        if (response.isSuccessful) {
            return response.body()?.data?.results?.map {
                Character(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    thumbnailUrl = "${it.thumbnail.path}.${it.thumbnail.extension}",
                    isFavorite = false
                )
            } ?: emptyList()
        } else {
            throw Exception("Error fetching characters: ${response.message()}")
        }
    }
}
