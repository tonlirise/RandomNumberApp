package com.example.randomnumberapp.numbers.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.randomnumberapp.databinding.FragmentNumbersBinding
import com.example.randomnumberapp.main.presentation.ShowFragment
import com.example.randomnumberapp.numbers.presentation.adapter.DiffUtilCallback
import com.example.randomnumberapp.numbers.presentation.adapter.NumbersAdapter
import com.example.randomnumberapp.numbers.presentation.adapter.OnNumberItemClickListener

class NumbersFragment : Fragment() {

    private var _numbersBinding: FragmentNumbersBinding? = null
    private val numbersBinding get() = _numbersBinding!!

    private var showFragment: ShowFragment = ShowFragment.Base()

    private lateinit var numberViewModel: NumbersViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showFragment = context as ShowFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _numbersBinding = FragmentNumbersBinding.inflate(inflater, container, false)
        return numbersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val diffUtilCallBack = DiffUtilCallback()
        val clickCallBack = object : OnNumberItemClickListener {
            override fun onClickListener(number: NumberUi) {
                TODO("Not yet implemented")
            }
        }
        val numAdapter = NumbersAdapter(diffUtilCallBack, clickCallBack)
        numbersBinding.recyclerHistory.adapter = numAdapter

        with(numberViewModel) {
            observeProgress(viewLifecycleOwner) { visibility ->
                numbersBinding.progressBar.visibility = visibility
            }

            observeCurrentState(viewLifecycleOwner) {

            }

            observeHistoryList(viewLifecycleOwner) { lsit ->
                numAdapter.map(lsit)
            }
        }

    }

    override fun onDetach() {
        super.onDetach()
        _numbersBinding = null
        showFragment = ShowFragment.Base()
    }
}