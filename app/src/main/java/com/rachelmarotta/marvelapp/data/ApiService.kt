package com.rachelmarotta.marvelapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private fun retrofit() = Retrofit.Builder()
        .baseUrl("developer.marvel.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> buildService(service:Class<T>) : T = retrofit().create(service)
}