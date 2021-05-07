package com.example.splits

import com.example.splits.models.Timer
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class TimerTest {

    private lateinit var timer: Timer

    @Before
    fun instantiateObject() {
        timer = Timer()
    }

    @Test
    fun incrementInterval() {
        timer.modifyInterval("+")
        assertThat(timer.interval).isEqualTo(1.1)
    }

    @Test
    fun decrementInterval() {
        timer.modifyInterval("-")
        assertThat(timer.interval).isEqualTo(0.9)
    }

    @Test
    fun incrementSplit() {
        timer.modifySplit("+")
        assertThat(timer.split).isEqualTo(1.6)
    }

    @Test
    fun decrementSplit() {
        timer.modifySplit("-")
        assertThat(timer.split).isEqualTo(1.4)
    }

}