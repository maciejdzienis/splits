package com.example.splits.viewmodels

import androidx.lifecycle.ViewModel
import com.example.splits.models.Interval
import com.example.splits.models.Timer

class MainViewModel: ViewModel() {

    val timer = Timer()
    val interval = Interval()


    fun returnIntervalString(): String {
        return interval.value.toString()
    }

    fun returnDelayString(): String {
        return timer.delay.toString()
    }

    fun returnSplitString(): String {
        return timer.split.toString()
    }

    fun stopJobs() {
        timer.stop()
        interval.stop()
    }
}