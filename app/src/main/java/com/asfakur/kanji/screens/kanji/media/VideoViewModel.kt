package com.asfakur.kanji.screens.kanji.media

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VideoViewModel : ViewModel() {
    val isPlaying = mutableStateOf(false)

    fun play() {
        isPlaying.value = true
    }

    fun pause() {
        isPlaying.value = false
    }

    fun onCompletion() {
        isPlaying.value = false
    }
}
