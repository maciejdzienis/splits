package com.example.splits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.splits.databinding.ActivityMainBinding
import com.example.splits.fragments.Recoil
import com.example.splits.fragments.Splits
import com.example.splits.models.Timer
import com.example.splits.room.LikedDatabase
import com.example.splits.room.LikedItem
import com.example.splits.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Job
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private  lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var tabSelected by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // VIEW MODEL

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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

        //SET INTERVAL FROM SHARED PREFERENCES

        val pref = getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE)
        pref.apply {
            val interval = getString("INTERVAL", "1.0")
            if (interval != null) {
                viewModel.interval.value = interval.toDouble()
            }
        }


        //FRAGMENT MANAGER

        val fragmentSplits = Splits()
        val fragmentRecoil = Recoil()

        supportFragmentManager.commit {
            replace(R.id.fragment, fragmentSplits)
        }

        //RECEIVE AND SET EXTRAS FROM LIKED IF EXIST

        val delayValue = intent.getData("delay_value")
        val splitValue = intent.getData("split_value")

        if (delayValue != "intent is null") {
            viewModel.timer.delay = delayValue.toInt()
            viewModel.timer.split = splitValue.toDouble()
        }

        //ONCLICK EVENTS

        binding.btnStart.setOnClickListener {
            if (infiniteLoopJob?.isActive == true || infiniteIntervalLoopJob?.isActive == true) {
                binding.btnStart.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_baseline_start
                    )
                )
                viewModel.stopJobs()
            } else {
                if (tabSelected == 0) viewModel.timer.playInfiniteLoop() else viewModel.interval.playInfiniteIntervalLoop()
                binding.btnStart.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_baseline_pause
                    )
                )
            }
        }

        //TABs ITEM ACTIONS
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                tabSelected = tab.position
                when (tab.position) {
                    0 -> {
                        supportFragmentManager.commit {
                        setCustomAnimations(
                            R.anim.from_left,
                            R.anim.fade_out
                        )
                        replace(R.id.fragment, fragmentSplits)
                        }
                    }
                    1 -> {
                        supportFragmentManager.commit {
                            setCustomAnimations(
                                R.anim.from_right,
                                R.anim.fade_out
                            )
                            replace(R.id.fragment, fragmentRecoil)
                        }
                    }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }
        })


        //MENU ITEM ACTIONS

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_liked -> {
                    likedNav()
                    true
                }
                R.id.action_add -> {
                    if (tabSelected == 0) {
                        val item = LikedItem(viewModel.returnDelayString(), viewModel.returnSplitString())
                        db.likedItemDao().insert(item)
                        showToast(getString(R.string.item_added))
                    } else {
                        val pref = getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE)
                        val editor = pref.edit()
                        editor
                            .putString("INTERVAL", viewModel.returnIntervalString())
                            .apply()
                        showToast(getString(R.string.saved))
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        tabSelected = binding.tabLayout.selectedTabPosition
        viewModel.stopJobs()
        binding.btnStart.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.ic_baseline_start
            )
        )

    }

    private fun showToast(text: String) {
        val text = text
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    private fun likedNav() {
        viewModel.stopJobs()
        //EXPLICIT INTENT
        var likedIntent = Intent(applicationContext, LikedActivity::class.java)
        startActivity(likedIntent)
    }

    //KOTLIN EXTENSION
    private fun Intent.getData(key: String): String {
        return extras?.getString(key) ?: "intent is null"
    }
}


//TASKS

var infiniteLoopJob: Job? = null
var infiniteIntervalLoopJob: Job? = null


