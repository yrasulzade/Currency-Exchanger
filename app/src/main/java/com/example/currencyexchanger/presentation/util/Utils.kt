package com.example.currencyexchanger.presentation.util

object Utils {

    inline fun <T> T?.ifNull(block: () -> Unit): T? {
        if (this == null) block()
        return this@ifNull
    }

    inline fun <T> T?.ifNonNull(block: (T) -> Unit): T? {
        this?.let(block)
        return this@ifNonNull
    }
}