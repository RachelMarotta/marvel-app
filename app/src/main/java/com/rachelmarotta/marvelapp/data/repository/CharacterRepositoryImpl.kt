package com.rachelmarotta.marvelapp.data.repository

import android.util.Log
import com.rachelmarotta.marvelapp.BuildConfig
import com.rachelmarotta.marvelapp.data.remote.MarvelService
import com.rachelmarotta.marvelapp.data.remote.utils.generateMd5Hash
import com.rachelmarotta.marvelapp.domain.model.Character
import com.rachelmarotta.marvelapp.domain.repository.CharacterRepository
import com.rachelmarotta.marvelapp.data.model.toDomain

class CharacterRepositoryImpl(private val marvelService: MarvelService) : CharacterRepository {

    override suspend fun getCharacters(offset: Int, limit: Int): List<Character> {
        val publicKey = BuildConfig.MARVEL_PUBLIC_KEY
        val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        val ts = System.currentTimeMillis().toString()
        val hash = generateMd5Hash("$ts$privateKey$publicKey")

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
}
