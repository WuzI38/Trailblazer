package com.app.trailblazer.activities

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.trailblazer.database.DatabaseHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.app.trailblazer.database.Record
import kotlinx.coroutines.Job

class TimerViewModel : ViewModel() {
    // Current timer state (from enum TimerState)
    private var _timerState = mutableStateOf(TimerState.NotStarted)
    // Time measured in seconds
    private val _time = mutableStateOf(0)
    // List of timer records from the database
    private val _records = mutableStateOf<List<Record>>(listOf())
    // Database Handler object used to communicate with the database
    private lateinit var dbHandler: DatabaseHelper

    private val timeApprox = mutableStateOf(0)

    // Coroutine status
    private var job: Job? = null

    // Measurement name
    val timerName = mutableStateOf("")

    // Getters
    val time: State<Int> get() = _time
    val timerState: State<TimerState> get() = _timerState
    val records: State<List<Record>> get() = _records

    // Setters
    fun submitName() {
        _timerState.value = TimerState.NameEntered
    }

    enum class TimerState {
        NotStarted, Running, Paused, NameEntered
    }

    // Get records from database
    fun initRecords(context: Context) {
        dbHandler = DatabaseHelper(context)
        _records.value = dbHandler.getAllRecords()
    }

    // Repeat select query after record addition deletion
    fun refreshRecords() {
        _records.value = dbHandler.getAllRecords()
    }

    // Reset time to 0 seconds
    private fun resetTimer() {
        job?.cancel()
        timeApprox.value = 0
        _time.value = 0
    }

    // Reset timer and start it in the background using coroutine
    fun startTimer() {
        resetTimer()
        _timerState.value = TimerState.Running

        job = viewModelScope.launch {
            while (_timerState.value != TimerState.NotStarted && _time.value < 60*60*24 - 1) {
                delay(50L)
                if(_timerState.value == TimerState.Running) {
                    // As it is impossible to interfere with the coroutine during
                    // delay check every 50 ms if the pause button was clicked
                    timeApprox.value += 50
                    if(timeApprox.value >= 1000) {
                        timeApprox.value = 0
                        _time.value += 1
                    }
                }
            }
        }
    }

    // Stop timer and add new timer record to the database
    fun stopTimer() {
        _timerState.value = TimerState.NotStarted
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        dbHandler.addRecord(Record(0, currentDate, _time.value, timerName.value))
        resetTimer()
        _records.value = dbHandler.getAllRecords()
    }

    // Pause timer after the button was clicked
    fun togglePause() {
        _timerState.value = when (_timerState.value) {
            TimerState.Running -> TimerState.Paused
            TimerState.Paused -> TimerState.Running
            else -> TimerState.NameEntered
        }
    }
}
