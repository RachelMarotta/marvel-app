package com.rachelmarotta.marvelapp.data

import retrofit2.Call
import retrofit2.http.GET

interface MarvelService {

    @GET("/v1/public/characters")
    fun getList() : Call<Any>
}