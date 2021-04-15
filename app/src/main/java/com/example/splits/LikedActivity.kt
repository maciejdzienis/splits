package com.example.splits

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar

class LikedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liked)

        //RECYCLER VIEW INIT
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LikedAdapter()

        //MENU ITEM ACTIONS
        val bottomAppBar: BottomAppBar = findViewById(R.id.bottomAppBar)
        bottomAppBar?.setOnMenuItemClickListener { menuItem -> when (menuItem.itemId) {
            R.id.action_back -> {
                backNav()
                true
            }
            else -> false
        } }
    }

    private fun backNav() {
        var backIntent = Intent(applicationContext, MainActivity::class.java)
        startActivity(backIntent)
    }
}