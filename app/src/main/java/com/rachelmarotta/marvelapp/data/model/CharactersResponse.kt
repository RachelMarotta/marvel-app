package com.rachelmarotta.marvelapp.data.model

data class CharactersResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: Data,
    val tag: String,
    val status: String
)