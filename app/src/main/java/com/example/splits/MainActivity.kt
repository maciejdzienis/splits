package com.example.splits

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.splits.databinding.ActivityMainBinding
import com.example.splits.room.LikedDatabase
import com.example.splits.room.LikedItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // VIEW BINDING

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // ROOM DATABASE
        val db = Room.databaseBuilder(
            applicationContext,
            LikedDatabase::class.java, "liked_database"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        //INIT
        //RECEIVE AND SET EXTRAS FROM LIKED IF EXIST
        var bundle :Bundle ?= intent.extras
        if (bundle != null) {
            var delayValue = bundle.getString("delay_value")
            var splitValue = bundle.getString("split_value")
            binding.tvDelay.text = delayValue
            binding.tvTimer.text = splitValue
            timer.delay = delayValue!!.toInt()
            timer.split = splitValue!!.toDouble()
        } else {
            //SET DEFAULT
            binding.tvDelay.text = timer.delay.toString()
            binding.tvTimer.text = timer.split.toString()
        }
        //ONCLICK EVENTS

        binding.btnStart.setOnClickListener {
            if (infiniteLoopJob?.isActive == true){
                binding.btnStart.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_baseline_start
                    )
                )
                timer.stop()
            } else {
                timer.playInfiniteLoop()
                binding.btnStart.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_baseline_pause
                    )
                )
            }
        }


        binding.btnAddDelay.setOnClickListener {
            timer.modifyDelay("+")
            binding.tvDelay.text = timer.delay.toString()
        }

        binding.btnReduceDelay.setOnClickListener {
            timer.modifyDelay("-")
            binding.tvDelay.text = timer.delay.toString()
        }

        binding.btnAddSplit.setOnClickListener {
            timer.modifySplit("+")
            binding.tvTimer.text = timer.split.toString()
        }

        binding.btnReduceSplit.setOnClickListener {
            timer.modifySplit("-")
            binding.tvTimer.text = timer.split.toString()
        }

        //MENU ITEM ACTIONS

        binding.bottomAppBar?.setOnMenuItemClickListener { menuItem -> when (menuItem.itemId) {
            R.id.action_liked -> {
                likedNav()
                true
            }
            R.id.action_add -> {
                val item = LikedItem(timer.delay.toString(), timer.split.toString())
                db.likedDao().insert(item)
                addLiked()
                true
            }
            else -> false
        } }
    }

    private fun showToast(text: String) {
        val text = text
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    private fun likedNav() {
        timer.stop()
        //EXPLICIT INTENT
        var likedIntent = Intent(applicationContext, LikedActivity::class.java)
        startActivity(likedIntent)
    }

    private fun addLiked() {
        val added = resources.getString(R.string.item_added)
            showToast(added)

    }
}


//TASKS

var infiniteLoopJob: Job? = null

var timer = Timer()



