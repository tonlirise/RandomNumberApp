package com.example.randomnumberapp.numbers.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.randomnumberapp.R
import com.example.randomnumberapp.detail.presentation.DetailFragment
import com.example.randomnumberapp.main.presentation.ShowFragment

class NumbersFragment : Fragment() {
    private var showFragment: ShowFragment = ShowFragment.Base()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showFragment = context as ShowFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_numbers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnGetFact = view.findViewById<Button>(R.id.btnGetFact)
        btnGetFact.setOnClickListener {
            showFragment.show(DetailFragment.getNewInstance("This is some test string 1 .. 2 .. 3"))
        }
    }

    override fun onDetach() {
        super.onDetach()
        showFragment = ShowFragment.Base()
    }
}