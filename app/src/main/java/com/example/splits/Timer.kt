package com.example.splits

import android.media.AudioManager
import android.media.ToneGenerator
import kotlinx.coroutines.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.roundToLong

class Timer {
    var delay :Int = 5
    var split :Double = 1.5
    var interval :Double = 1.0

    fun playInfiniteLoop() {
        var i = 0
        val tg = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        infiniteLoopJob = GlobalScope.launch(Dispatchers.Default) {
            while (isActive) {
                while (i < delay) {
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP, 35)
                    delay(1000)
                    ++i
                }
                i = 0
                tg.startTone(ToneGenerator.TONE_PROP_PROMPT, 200)
                var splitInMillis = split * 1000
                delay(splitInMillis.toLong())
                tg.startTone(ToneGenerator.TONE_PROP_PROMPT, 200)
                delay(1000)
            }
        }
    }

    fun playInfiniteIntervalLoop() {
        val tg = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        infiniteIntervalLoopJob = GlobalScope.launch(Dispatchers.Default) {
            while (isActive) {
                tg.startTone(ToneGenerator.TONE_PROP_BEEP, 35)
                var splitInMillis = interval * 1000
                delay(splitInMillis.toLong())
            }
        }
    }

    fun stop() {
        infiniteLoopJob?.cancel()
        infiniteIntervalLoopJob?.cancel()
    }


    fun modifyDelay( modifier: String) {
        when(modifier) {
            "+" -> delay++
            "-" -> if (delay > 2) delay-- else delay
        }
    }

    fun modifySplit( modifier: String) {
        val increment = 0.1
        if (split >= 0.1) {
            when (modifier) {
                "+" -> split += increment
                "-" -> split -= increment
            }
        } else {
            when (modifier) {
                "+" -> split += increment
            }
        }
        val setSymbol = DecimalFormatSymbols()
        setSymbol.decimalSeparator = '.'
        val df = DecimalFormat("#.#", setSymbol)
        df.roundingMode = RoundingMode.HALF_UP
        val converted = df.format(split).toDouble()
        split = converted
    }

    fun modifyInterval( modifier: String) {
        val increment = 0.1
        if (interval >= 0.1) {
            when (modifier) {
                "+" -> interval += increment
                "-" -> interval -= increment
            }
        } else {
            when (modifier) {
                "+" -> interval += increment
            }
        }
        val setSymbol = DecimalFormatSymbols()
        setSymbol.decimalSeparator = '.'
        val df = DecimalFormat("#.#", setSymbol)
        df.roundingMode = RoundingMode.HALF_UP
        val converted = df.format(interval).toDouble()
        interval = converted
    }

}