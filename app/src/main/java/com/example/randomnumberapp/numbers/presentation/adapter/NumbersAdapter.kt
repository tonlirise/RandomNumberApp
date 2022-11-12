package com.example.randomnumberapp.numbers.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.randomnumberapp.databinding.ItemNumberLayoutBinding
import com.example.randomnumberapp.numbers.presentation.Mapper
import com.example.randomnumberapp.numbers.presentation.NumberUi

class NumbersAdapter(
    diffUtilCallback: DiffUtilCallback,
    private val itemCallback: OnNumberItemClickListener
) : ListAdapter<NumberUi, NumberViewHolder>(diffUtilCallback), Mapper.Unit<List<NumberUi>> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val binding = ItemNumberLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumberViewHolder(binding, itemCallback)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.map(getItem(position))
    }

    override fun map(source: List<NumberUi>) {
        submitList(source)
    }
}

interface OnNumberItemClickListener {
    fun onClickListener(number: NumberUi)
}