package com.example.splits.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.splits.viewmodels.MainViewModel
import com.example.splits.R

class Recoil: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.fragment_recoil,
            container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val btnReduceSplit: Button = view.findViewById(R.id.btnReduceInterval)
        val btnAddSplit: Button = view.findViewById(R.id.btnAddInterval)
        val tvTimer: TextView = view.findViewById(R.id.tvInterval)

        tvTimer.text = viewModel.returnIntervalString()

        btnAddSplit.setOnClickListener {
            viewModel.interval.modifyInterval("+")
            tvTimer.text = viewModel.returnIntervalString()
        }

        btnReduceSplit.setOnClickListener {
            viewModel.interval.modifyInterval("-")
            tvTimer.text = viewModel.returnIntervalString()
        }
    }

}