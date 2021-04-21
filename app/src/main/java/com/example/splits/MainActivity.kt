package com.example.splits

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.splits.databinding.ActivityMainBinding
import kotlinx.coroutines.Job

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // VIEW BINDING

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
            Log.d("TAG", timer.split.toString())
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
        val nothingToShow = resources.getString(R.string.empty_list)

        //EXPLICIT INTENT
        var likedIntent = Intent(applicationContext, LikedActivity::class.java)
        var emptyList = Toast.makeText(applicationContext, nothingToShow, Toast.LENGTH_SHORT)
        if (LikedDataBase.likedDelay.isNotEmpty()) startActivity(likedIntent) else emptyList.show()
    }

    private fun addLiked() {
        val added = resources.getString(R.string.item_added)
        val alreadyAdded = resources.getString(R.string.item_already_added)
        val delay = timer.delay.toString()
        val split = timer.split.toString()
        if (delay !in LikedDataBase.likedDelay || split !in LikedDataBase.likedSplit) {
            LikedDataBase.likedDelay.add(delay)
            LikedDataBase.likedSplit.add(split)
            showToast(added)
        } else showToast(alreadyAdded)
    }
}


//TASKS

var infiniteLoopJob: Job? = null

var timer = Timer()



