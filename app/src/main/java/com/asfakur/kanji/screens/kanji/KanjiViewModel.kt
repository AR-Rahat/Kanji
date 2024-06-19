package com.asfakur.kanji.screens.kanji

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asfakur.kanji.models.Kanji
import com.asfakur.kanji.repository.KanjiRepo
import kotlinx.coroutines.launch

class KanjiViewModel: ViewModel() {
    private val repository = KanjiRepo()
    private val _kanjiDetails: MutableLiveData<Kanji> by lazy {
        MutableLiveData<Kanji>()
    }
    val kanjiDetails: LiveData<Kanji>
        get() = _kanjiDetails

    fun fetchKanjiDetails(week: String, id: String) = viewModelScope.launch {
        val response = repository.fetchKanjiDetails(week,id)
        _kanjiDetails.value = response
    }
}