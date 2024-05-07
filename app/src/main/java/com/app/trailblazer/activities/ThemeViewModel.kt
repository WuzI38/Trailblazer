package com.app.trailblazer.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThemeViewModel: ViewModel() {
    private val _darkTheme = MutableLiveData(true)
    val darkTheme: LiveData<Boolean> get() = _darkTheme

    fun toggleTheme() {
        _darkTheme.value = _darkTheme.value?.not()
    }
}