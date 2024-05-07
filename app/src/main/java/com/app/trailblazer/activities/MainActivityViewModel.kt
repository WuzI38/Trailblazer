package com.app.trailblazer.activities

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.trailblazer.trails.Trail
import kotlinx.coroutines.launch
import com.app.trailblazer.trails.TrailListCreator
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val _allTrails = MutableLiveData<List<Trail>>()
    private val _filteredTrails = MutableLiveData<List<Trail>>()

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext

    val viewState = MutableLiveData(ViewState.HOME)
    val filteredTrails: LiveData<List<Trail>> get() = _filteredTrails
    var trailIndex = MutableLiveData(0)

    enum class ViewState {
        HOME, TRAIL, TIMER
    }

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

    init {
        initializeTrails()
    }

    private fun initializeTrails() {
        viewModelScope.launch(Dispatchers.Main) {
            _allTrails.value = TrailListCreator.getTrails(context)
            updateTrails()
        }
    }

    private fun updateTrails() {
        _filteredTrails.value = _allTrails.value?.filter {
            (difficulty.isBlank() || it.difficulty.equals(difficulty, ignoreCase = true)) &&
                    (name.isBlank() || it.trailName.contains(name, ignoreCase = true))
        }
    }
}
