package com.example.splits

import android.media.AudioManager
import android.media.ToneGenerator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class Timer {
    var delay = 5
    var seconds = 1L
    var tenthSeconds = 5L

    fun playInfiniteLoop() {
        var i = 0
        val tg = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        infiniteLoopJob = GlobalScope.launch {
            while (isActive) {
                while (i < delay) {
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP, 35)
                    delay(1000)
                    ++i
                }
                i = 0
                tg.startTone(ToneGenerator.TONE_PROP_PROMPT, 200)
                delay((seconds * 1000) + (tenthSeconds + 100))
                tg.startTone(ToneGenerator.TONE_PROP_PROMPT, 200)
                delay(1000)
            }
        }
    }

    fun stop() {
        infiniteLoopJob?.cancel()
    }

    fun threshold(): String {
        return "$seconds.$tenthSeconds"
    }

    fun modifyDelay( modifier: String) {
        when(modifier) {
            "+" -> delay++
            "-" -> if (delay > 2) delay-- else delay
        }
    }

    private fun addSecond(): Long {
        tenthSeconds = 0
        return seconds++
    }

    private fun subtractSecond(): Long {
        if (seconds > 0) {
            tenthSeconds = 9
            seconds--
        }
        return seconds
    }

    fun addTime() {
        when (tenthSeconds) {
            9L -> addSecond()
            in 0..8 -> tenthSeconds++
        }
    }

    fun reduceTime() {
        when (tenthSeconds) {
            0L -> subtractSecond()
            in 1..9 -> tenthSeconds--
        }
    }
}