package com.example.randomnumberapp.numbers.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.randomnumberapp.databinding.ItemNumberLayoutBinding
import com.example.randomnumberapp.numbers.presentation.NumberUi

class NumberViewHolder(
    private val binding: ItemNumberLayoutBinding,
    private val onNumberItemClickListener: OnNumberItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun map(numberUi: NumberUi) {
        numberUi.map(binding.titleTextView, binding.subTitleTextView)
        binding.root.setOnClickListener {
            onNumberItemClickListener.onClickListener(numberUi)
        }
    }
}