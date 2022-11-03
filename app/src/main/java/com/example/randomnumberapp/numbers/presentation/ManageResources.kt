package com.example.randomnumberapp.numbers.presentation

import android.content.Context
import androidx.annotation.StringRes

interface ManageResources {

    fun getString(@StringRes idRes: Int): String

    class Base(val context: Context) : ManageResources {
        override fun getString(idRes: Int) = context.getString(idRes)
    }
}