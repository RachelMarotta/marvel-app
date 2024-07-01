package com.rachelmarotta.marvelapp.data.model

import com.rachelmarotta.marvelapp.domain.model.Character

fun Result.toDomain(): Character {
    return Character(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnailUrl = "${this.thumbnail.path}.${this.thumbnail.extension}"
            .replace("http", "https")
    )
}
