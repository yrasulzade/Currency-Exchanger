package com.example.currencyexchanger.presentation.extensions

import android.widget.Button
import android.widget.EditText

fun EditText.clearEditText() {
    return this.setText("")
}
fun Button.enable() {
    this.isEnabled = true
}

fun Button.disable() {
    this.isEnabled = false
}
