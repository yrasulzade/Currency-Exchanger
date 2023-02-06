package com.example.currencyexchanger.domain.model

data class ExchangeRate(
    val base: String, val date: String, val rates: List<CurrencyRate>
)

data class CurrencyRate(val currencyId: String, val rate: Double)
