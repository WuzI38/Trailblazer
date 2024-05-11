package com.app.trailblazer.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThemeViewModel: ViewModel() {
    // Is application in dark theme
    private val _darkTheme = MutableLiveData(true)
    val darkTheme: LiveData<Boolean> get() = _darkTheme

    // toggle theme (light theme or dark theme)
    fun toggleTheme() {
        _darkTheme.value = _darkTheme.value?.not()
    }
}