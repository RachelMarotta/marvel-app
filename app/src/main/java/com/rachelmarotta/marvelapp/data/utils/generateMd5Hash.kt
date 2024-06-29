package com.rachelmarotta.marvelapp.data.utils

import java.security.MessageDigest

fun generateMd5Hash(input: String): String {
    val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}