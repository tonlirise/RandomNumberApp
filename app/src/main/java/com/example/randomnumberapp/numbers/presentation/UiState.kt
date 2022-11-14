package com.example.randomnumberapp.numbers.presentation

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class UiState {

    abstract fun apply(inputEditText: TextInputEditText, inputLayout: TextInputLayout)

    class Success : UiState() {
        override fun apply(inputEditText: TextInputEditText, inputLayout: TextInputLayout) {
            inputEditText.setText("")
        }
    }

    abstract class AbstractError(private val message: String, private val isErrorEnabled: Boolean) : UiState() {
        override fun apply(inputEditText: TextInputEditText, inputLayout: TextInputLayout) {
            inputLayout.error = message
            inputLayout.isErrorEnabled = isErrorEnabled
        }
    }

    data class ShowError(private val message: String) : AbstractError(message, true)
    class ClearError() : AbstractError("", false)
}
