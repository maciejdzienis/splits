package com.example.splits

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.splits.room.LikedDatabase
import com.example.splits.room.LikedItem
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LikedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liked)

        val db = Room.databaseBuilder(
            this,
            LikedDatabase::class.java, "liked_database"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        var globalData = db.likedItemDao().getAll()



        //RECYCLER VIEW INIT
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = LikedAdapter(globalData)

        //MENU ITEM ACTIONS
        val bottomAppBar: BottomAppBar = findViewById(R.id.bottomAppBar)
        bottomAppBar.setOnMenuItemClickListener { menuItem -> when (menuItem.itemId) {
            R.id.action_back -> {
                backNav()
                true
            }
            R.id.action_delete -> {
                if (globalData.isNotEmpty()) {
                    MaterialAlertDialogBuilder(this)
                        .setTitle(resources.getString(R.string.delete_all_title))
                        .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                            // Respond to negative button press
                        }
                        .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                            GlobalScope.launch {
                                db.likedItemDao().deleteAll()
                            }
                            backNav()
                        }
                        .show()
                } else {
                    Toast.makeText(this, getString(R.string.nothing_to_delete), Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> false
        } }
    }

    private fun backNav() {
        finish()
    }
}