package com.example.splits

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.splits.databinding.ActivityMainBinding
import com.example.splits.fragments.Counter
import com.example.splits.fragments.Recoil
import com.example.splits.fragments.Splits
import com.example.splits.room.LikedDatabase
import com.example.splits.room.LikedItem
import com.google.android.material.tabs.TabLayout
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

        //FRAGMENT MANAGER

        val fm = supportFragmentManager
        val fragmentSplits = Splits()
        val fragmentRecoil = Recoil()
        val fragmentTimer = Counter()
        fm.beginTransaction().replace(R.id.fragment, fragmentSplits).commit()


        //RECEIVE AND SET EXTRAS FROM LIKED IF EXIST

        val delayValue = intent.getData("delay_value")
        val splitValue = intent.getData("split_value")

        if (delayValue != "intent is null") {
            timer.delay = delayValue.toInt()
            timer.split = splitValue.toDouble()
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

        //TABs ITEM ACTIONS

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab : TabLayout.Tab) {
                when (tab.position) {
                    0 -> fm.beginTransaction().replace(R.id.fragment, fragmentSplits).commit()
                    1 -> fm.beginTransaction().replace(R.id.fragment, fragmentRecoil).commit()
                    2 -> fm.beginTransaction().replace(R.id.fragment, fragmentTimer).commit()
                }

            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }
        })


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

    //KOTLIN EXTENSION
    private fun Intent.getData(key: String): String {
        return extras?.getString(key) ?: "intent is null"
    }
}


//TASKS

var infiniteLoopJob: Job? = null

var timer = Timer()



