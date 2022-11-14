package com.example.randomnumberapp.numbers.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.randomnumberapp.databinding.FragmentNumbersBinding
import com.example.randomnumberapp.detail.presentation.DetailFragment
import com.example.randomnumberapp.main.presentation.ShowFragment
import com.example.randomnumberapp.main.sl.ProvideViewModel
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

        numberViewModel = (requireActivity() as ProvideViewModel).provideViewModel(NumbersViewModel::class.java, this)

        _numbersBinding = FragmentNumbersBinding.inflate(inflater, container, false)
        return numbersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val diffUtilCallBack = DiffUtilCallback()
        val clickCallBack = object : OnNumberItemClickListener {
            override fun onClickListener(number: NumberUi) {
                showFragment.show(DetailFragment.getNewInstance(number.ui()))
            }
        }
        val numAdapter = NumbersAdapter(diffUtilCallBack, clickCallBack)
        numbersBinding.recyclerHistory.adapter = numAdapter

        with(numbersBinding) {
            inputEditText.addTextChangedListener(object : SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) = with(numbersBinding.inputLayout) {
                    numberViewModel.clearError()
                }
            })

            btnGetFact.setOnClickListener {
                numberViewModel.fetchNumberFact(inputEditText.text.toString())
            }

            btnGetRandomFact.setOnClickListener {
                numberViewModel.fetchRandomNumberFact()
            }
        }

        with(numberViewModel) {
            observeProgress(viewLifecycleOwner) { visibility ->
                numbersBinding.progressBar.visibility = visibility
            }

            observeCurrentState(viewLifecycleOwner) {
                it.apply(numbersBinding.inputEditText, numbersBinding.inputLayout)
            }

            observeHistoryList(viewLifecycleOwner) { lsit ->
                numAdapter.map(lsit)
            }
        }

        numberViewModel.init(savedInstanceState == null)
    }

    override fun onDetach() {
        super.onDetach()
        _numbersBinding = null
        showFragment = ShowFragment.Base()
    }
}

abstract class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun afterTextChanged(p0: Editable?) = Unit
}