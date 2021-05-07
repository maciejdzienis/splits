package com.example.splits.models

import android.media.AudioManager
import android.media.ToneGenerator
import com.example.splits.infiniteIntervalLoopJob
import kotlinx.coroutines.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.roundToLong

class Interval {
    var value :Double = 1.0

    fun playInfiniteIntervalLoop() {
        val tg = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        infiniteIntervalLoopJob = GlobalScope.launch(Dispatchers.Default) {
            while (isActive) {
                tg.startTone(ToneGenerator.TONE_PROP_BEEP, 35)
                var splitInMillis = value * 1000
                delay(splitInMillis.toLong())
            }
        }
    }

    fun stop() {
        infiniteIntervalLoopJob?.cancel()
    }

    fun modifyInterval( modifier: String) {
        val increment = 0.1
        if (value >= 0.1) {
            when (modifier) {
                "+" -> value += increment
                "-" -> value -= increment
            }
        } else {
            when (modifier) {
                "+" -> value += increment
            }
        }
        val setSymbol = DecimalFormatSymbols()
        setSymbol.decimalSeparator = '.'
        val df = DecimalFormat("#.#", setSymbol)
        df.roundingMode = RoundingMode.HALF_UP
        val converted = df.format(value).toDouble()
        value = converted
    }

}