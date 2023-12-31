package com.example.a2023_q2_mironov.ui.util

import android.text.Editable
import android.text.TextWatcher

fun addTextWatcher(textChange: () -> Unit): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            textChange()
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
}