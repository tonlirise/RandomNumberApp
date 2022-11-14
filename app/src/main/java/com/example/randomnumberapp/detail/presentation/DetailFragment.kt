package com.example.randomnumberapp.detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.randomnumberapp.R

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val twDetail = view.findViewById<TextView>(R.id.twDetail)
        twDetail.text = arguments?.getString(DETAIL_STRING)
    }

    companion object {
        private const val DETAIL_STRING = "DETAIL"

        fun getNewInstance(information: String) = DetailFragment().apply {
            arguments = Bundle().apply {
                putString(DETAIL_STRING, information)
            }
        }
    }
}
