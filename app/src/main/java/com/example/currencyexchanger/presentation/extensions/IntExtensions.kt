package com.example.currencyexchanger.presentation.extensions

fun Double.formatTwoDecimal(): String {
    return String.format("%.2f", this)
}

fun Double.formatFourDecimal(): String {
    return String.format("%.4f", this)
}
