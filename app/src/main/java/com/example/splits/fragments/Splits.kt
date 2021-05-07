package com.example.splits.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.splits.R
import com.example.splits.viewmodels.MainViewModel

class Splits: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splits,
            container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val btnAddDelay: Button = view.findViewById(R.id.btnAddDelay)
        val tvDelay: TextView = view.findViewById(R.id.tvDelay)
        val btnReduceDelay: Button = view.findViewById(R.id.btnReduceDelay)
        val btnReduceSplit: Button = view.findViewById(R.id.btnReduceSplit)
        val btnAddSplit: Button = view.findViewById(R.id.btnAddSplit)
        val tvTimer: TextView = view.findViewById(R.id.tvTimer)

        tvDelay.text = viewModel.returnDelayString()
        tvTimer.text = viewModel.returnSplitString()

        btnAddDelay.setOnClickListener {
            viewModel.timer.modifyDelay("+")
            tvDelay.text = viewModel.returnDelayString()
        }

        btnReduceDelay.setOnClickListener {
            viewModel.timer.modifyDelay("-")
            tvDelay.text = viewModel.returnDelayString()
        }

        btnAddSplit.setOnClickListener {
            viewModel.timer.modifySplit("+")
            tvTimer.text = viewModel.returnSplitString()
        }

        btnReduceSplit.setOnClickListener {
            viewModel.timer.modifySplit("-")
            tvTimer.text = viewModel.returnSplitString()
        }
    }


}