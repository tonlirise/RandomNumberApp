package com.example.randomnumberapp.numbers.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.randomnumberapp.numbers.presentation.NumberUi

class DiffUtilCallback : DiffUtil.ItemCallback<NumberUi>() {
    override fun areItemsTheSame(oldItem: NumberUi, newItem: NumberUi): Boolean {
        return oldItem.map(newItem)
    }

    override fun areContentsTheSame(oldItem: NumberUi, newItem: NumberUi): Boolean {
        return oldItem == newItem
    }
}