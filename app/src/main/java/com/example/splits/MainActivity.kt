package com.example.splits

import android.content.res.ColorStateList
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //INIT

        val delayTextView: TextView = findViewById(R.id.tvDelay)
        val timeTextView: TextView = findViewById(R.id.tvTimer)
        val addDelayButton: Button = findViewById(R.id.btnAddDelay)
        val reduceDelayButton: Button = findViewById(R.id.btnReduceDelay)
        val addTimeButton: Button = findViewById(R.id.btnAddSplit)
        val reduceTimeButton: Button = findViewById(R.id.btnReduceSplit)
        val startButton: FloatingActionButton = findViewById(R.id.btnStart)
        val stopButton: FloatingActionButton = findViewById(R.id.btnStop)
        val infiniteLoopSwitch: FloatingActionButton = findViewById(R.id.btnLoop)

        delayTextView.text = timer.delay.toString()
        timeTextView.text = timer.threshold()

        //ON CLICK EVENTS

        startButton.setOnClickListener {
            when (timer.infiniteLoop) {
                false -> {
                    timer.playOnce()
                    startButton.hide()
                    stopButton.show()
                }
                true -> {
                    timer.playInfiniteLoop()
                    startButton.hide()
                    stopButton.show()
                }
            }
        }

        stopButton.setOnClickListener() {
            timer.stop()
            stopButton.hide()
            startButton.show()
        }

        infiniteLoopSwitch.setOnClickListener {
            when (timer.infiniteLoop) {
                false -> {
                    timer.infiniteLoop = true
                    infiniteLoopSwitch.backgroundTintList = ColorStateList.valueOf(
                        Color.parseColor(
                            "#ff7961"
                        )
                    )
                }
                true -> {
                    timer.infiniteLoop = false
                    infiniteLoopSwitch.backgroundTintList = ColorStateList.valueOf(
                        Color.parseColor(
                            "#ff03dac5"
                        )
                    )
                }
            }
        }

        addDelayButton.setOnClickListener {
            timer.modifyDelay("+")
            delayTextView.text = timer.delay.toString()
        }

        reduceDelayButton.setOnClickListener {
            timer.modifyDelay("-")
            delayTextView.text = timer.delay.toString()
        }

        addTimeButton.setOnClickListener {
            timer.addTime()
            timeTextView.text = timer.threshold()
        }

        reduceTimeButton.setOnClickListener {
            timer.reduceTime()
            timeTextView.text = timer.threshold()
        }
    }
}


//TASKS

var loopJob: Job? = null

var infiniteLoopJob: Job? = null


var timer = Timer()


class Timer {
    var delay = 5
    var seconds = 1L
    var tenthSeconds = 5L
    var infiniteLoop = false

    fun playOnce() {
        var i = 0
        val tg = ToneGenerator(AudioManager.STREAM_ALARM, 1000)
        loopJob = GlobalScope.launch {
            while (i < delay) {
                tg.startTone(ToneGenerator.TONE_PROP_BEEP, 35)
                delay(965)
                ++i
            }
            i = 0
            tg.startTone(ToneGenerator.TONE_PROP_PROMPT, 200)
            delay((seconds * 1000) + (tenthSeconds + 100))
            tg.startTone(ToneGenerator.TONE_PROP_PROMPT, 200)
        }

    }

    fun playInfiniteLoop() {
        var i = 0
        val tg = ToneGenerator(AudioManager.STREAM_ALARM, 1000)
        infiniteLoopJob = GlobalScope.launch {
            while (isActive) {
                while (i < delay) {
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP, 35)
                    delay(965)
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
        loopJob?.cancel()
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

