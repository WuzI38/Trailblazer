package com.lab.trailblazer.activities.timer

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab.trailblazer.database.DatabaseHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.lab.trailblazer.database.Record

class TimerViewModel : ViewModel() {
    val time = mutableIntStateOf(0)
    private val timerRunning = mutableStateOf(false)
    val timerStopped = mutableStateOf(false)

    fun startTimer() {
        if (!timerRunning.value) {
            timerRunning.value = true
            timerStopped.value = false
            viewModelScope.launch {
                while (timerRunning.value && time.intValue < 60*60*24 - 1) {
                    delay(1000L)
                    if(!timerStopped.value && timerRunning.value) {
                        time.intValue += 1
                    }
                }
                timerRunning.value = false
            }
        }
    }

    fun stopTimer(context: Context) {
        timerRunning.value = false
        val dbHandler = DatabaseHelper(context)
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        dbHandler.addRecord(Record(currentDate, time.intValue))
        time.intValue = 0
    }

    fun togglePause() {
        timerStopped.value = !timerStopped.value
    }
}