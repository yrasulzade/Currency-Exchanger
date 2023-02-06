package com.example.currencyexchanger.domain.converter

import com.example.currencyexchanger.data.model.ExchangeRatesDto
import com.example.currencyexchanger.domain.model.CurrencyRate
import com.example.currencyexchanger.domain.model.ExchangeRate

fun ExchangeRatesDto.toDomain(): ExchangeRate {
    val mutableList = mutableListOf<CurrencyRate>()
    this.rates.forEach {
        mutableList.add(CurrencyRate(it.key, it.value))
    }
    return ExchangeRate(this.base, this.date, mutableList)
}