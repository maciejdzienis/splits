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

class Splits: Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splits,
            container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddDelay: Button = view.findViewById(R.id.btnAddDelay)
        val tvDelay: TextView = view.findViewById(R.id.tvDelay)
        val btnReduceDelay: Button = view.findViewById(R.id.btnReduceDelay)
        val btnReduceSplit: Button = view.findViewById(R.id.btnReduceSplit)
        val btnAddSplit: Button = view.findViewById(R.id.btnAddSplit)
        val tvTimer: TextView = view.findViewById(R.id.tvTimer)


        btnAddDelay.setOnClickListener {
            timer.modifyDelay("+")
            tvDelay.text = timer.delay.toString()
        }

        btnAddDelay.setOnClickListener {
            timer.modifyDelay("+")
            tvDelay.text = timer.delay.toString()
        }

        btnReduceDelay.setOnClickListener {
            timer.modifyDelay("-")
            tvDelay.text = timer.delay.toString()
        }

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