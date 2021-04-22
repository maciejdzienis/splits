package com.example.splits

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.splits.room.LikedDatabase
import com.example.splits.room.LikedItem
import kotlinx.coroutines.Job

class LikedAdapter(private val likedList: List<LikedItem>): RecyclerView.Adapter<LikedViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val likedItem = layoutInflater.inflate(R.layout.liked_item, parent, false)
        return LikedViewHolder(likedItem)
    }

    override fun getItemCount(): Int {
        return likedList.size
    }

    override fun onBindViewHolder(holder: LikedViewHolder, position: Int) {
        val delay: TextView = holder.view.findViewById(R.id.tvLikedDelayValue)
        val split: TextView = holder.view.findViewById(R.id.tvLikedSplitValue)

        delay.text =  likedList[position].delayValue
        split.text =  likedList[position].splitValue

        holder.view.setOnClickListener() {
            //EXPLICIT INTENT
            val delay: TextView = holder.view.findViewById(R.id.tvLikedDelayValue)
            val split: TextView = holder.view.findViewById(R.id.tvLikedSplitValue)
            var setIntent = Intent(holder.view.context, MainActivity::class.java)
            setIntent.putExtra("delay_value", delay.text.toString());
            setIntent.putExtra("split_value", split.text.toString());
            holder.view.context.startActivity(setIntent)
        }

    }
}

class LikedViewHolder(val view: View): RecyclerView.ViewHolder(view)