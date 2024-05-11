package com.app.trailblazer.activities

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.trailblazer.trails.Trail
import kotlinx.coroutines.launch
import com.app.trailblazer.trails.TrailListCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    // All trails stored in trails.json as a trail object list
    private val _allTrails = MutableLiveData<List<Trail>>()
    // Subset of _allTrails after filtration
    private val _filteredTrails = MutableLiveData<List<Trail>>()
    // True if the trail list was initialized
    private val _isReady = MutableStateFlow(false)
    // The index of the currently displayed trail
    private val _trailIndex = MutableStateFlow(0)
    // Displayed trail name
    private val _currentTrailName = MutableStateFlow("")

    // Getters
    val filteredTrails: LiveData<List<Trail>> get() = _filteredTrails
    val trailIndex: StateFlow<Int> get() = _trailIndex
    val currentTrailName:StateFlow<String> get() = _currentTrailName

    // Setters
    var difficulty: String = ""
        set(value) {
            field = if(value in arrayOf("easy", "medium", "hard")) {
                value
            } else {
                ""
            }
            updateTrails()
        }

    var name: String = ""
        set(value) {
            field = value
            updateTrails()
        }

    fun setTrailIndex(index: Int) {
        _trailIndex.value = index
    }

    fun setTrailName(name: String) {
        _currentTrailName.value = name
    }

    // Application context
    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext

    // Current application state, determines which page to load
    // As the app is very simple there was no need to use navigation graph
    val viewState = MutableLiveData(ViewState.HOME)

    // Is trail list initialized
    val isReady = _isReady.asStateFlow()

    enum class ViewState {
        HOME, TRAIL, TIMER
    }

    init {
        initializeTrails()
    }

    // Transform json data into trail list
    private fun initializeTrails() {
        viewModelScope.launch(Dispatchers.Main) {
            _allTrails.value = TrailListCreator.getTrails(context)
            updateTrails()
            _isReady.value = true
        }
    }

    // Filter trails by name and difficulty
    private fun updateTrails() {
        _filteredTrails.value = _allTrails.value?.filter {
            (difficulty.isBlank() || it.difficulty.equals(difficulty, ignoreCase = true)) &&
                    (name.isBlank() || it.trailName.contains(name, ignoreCase = true))
        }
    }
}
