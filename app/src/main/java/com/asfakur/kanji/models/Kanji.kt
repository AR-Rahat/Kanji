package com.asfakur.kanji.models


data class Kanji(
    val id: String,
    val audio: String,
    val kanji: String,
    val meaning: String,
    val pdf: String,
    val usage: String,
    val video: String,
    val word: String
)