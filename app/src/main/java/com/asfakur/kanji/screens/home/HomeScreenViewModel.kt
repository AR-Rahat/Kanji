package com.asfakur.kanji.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asfakur.kanji.repository.WeekRepo
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {
    private val _weeks : MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

    val weeks : LiveData<List<String>>
        get() = _weeks
    private val repository = WeekRepo()
    fun fetchWeeks() = viewModelScope.launch {
        val response = repository.getWeeks()
        println(response)
        _weeks.value = response
    }
}