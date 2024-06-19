package com.asfakur.kanji.screens.kanjiList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asfakur.kanji.models.Kanji
import com.asfakur.kanji.repository.KanjiRepo
import kotlinx.coroutines.launch

class KanjiListViewModel: ViewModel() {
    private val _kanjiList: MutableLiveData<List<Kanji>> by lazy {
        MutableLiveData<List<Kanji>>()
    }
    val kanjiList : LiveData<List<Kanji>>
        get() = _kanjiList

    private val repository = KanjiRepo()
    fun fetchKanjiList(week: String)= viewModelScope.launch{
        val response = repository.getKanjiList(week)
        _kanjiList.value = response
    }
}