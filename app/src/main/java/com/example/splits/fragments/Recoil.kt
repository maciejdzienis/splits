package com.example.splits.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.splits.R
import com.example.splits.timer

class Recoil: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.fragment_recoil,
            container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnReduceSplit: Button = view.findViewById(R.id.btnReduceSplit2)
        val btnAddSplit: Button = view.findViewById(R.id.btnAddSplit2)
        val tvTimer: TextView = view.findViewById(R.id.tvTimer2)

        tvTimer.text = timer.split.toString()

        btnAddSplit.setOnClickListener {
            timer.modifySplit("+")
            tvTimer.text = timer.split.toString()
        }

        btnReduceSplit.setOnClickListener {
            timer.modifySplit("-")
            tvTimer.text = timer.split.toString()
        }
    }

}