package com.example.currencyexchanger.domain.repository

import com.example.currencyexchanger.domain.model.ExchangeRate

interface CurrencyRepository {
    suspend fun getLatestCurrencies(): ExchangeRate
}
