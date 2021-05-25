package com.example.splits.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.splits.models.Interval
import com.example.splits.models.Timer

class MainViewModel: ViewModel() {

    val timer = Timer()
    val interval = Interval()

    private val mutableDelay = MutableLiveData<String>()
    val delay: LiveData<String> get() = mutableDelay

    private val mutableSplit = MutableLiveData<String>()
    val split: LiveData<String> get() = mutableSplit


    fun returnIntervalString(): String {
        return interval.value.toString()
    }

    fun returnDelayString() {
        mutableDelay.value = timer.delay.toString()
    }

    fun returnSplitString(){
        mutableSplit.value = timer.split.toString()
    }

    fun stopJobs() {
        timer.stop()
        interval.stop()
    }
}