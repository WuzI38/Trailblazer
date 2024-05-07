package com.app.trailblazer.activities

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
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

class TimerViewModel : ViewModel() {
    val timerName = mutableStateOf("")
    val timerState = mutableStateOf(TimerState.NotStarted)

    enum class TimerState {
        NotStarted, Running, Paused
    }

    val time = mutableIntStateOf(0)
    private val timerRunning = mutableStateOf(false)

    fun startTimer() {
        if (timerState.value == TimerState.NotStarted) {
            timerState.value = TimerState.Running
            viewModelScope.launch {
                while (timerState.value == TimerState.Running && time.intValue < 60*60*24 - 1) {
                    delay(1000L)
                    if(timerState.value == TimerState.Running) {
                        time.intValue += 1
                    }
                }
                timerState.value = TimerState.NotStarted
            }
        }
    }

    fun stopTimer(context: Context) {
        timerState.value = TimerState.NotStarted
        val dbHandler = DatabaseHelper(context)
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        dbHandler.addRecord(Record(0, currentDate, time.intValue, timerName.value))
        time.intValue = 0
    }

    fun togglePause() {
        timerState.value = when (timerState.value) {
            TimerState.Running -> TimerState.Paused
            TimerState.Paused -> TimerState.Running
            else -> TimerState.NotStarted
        }
    }
}