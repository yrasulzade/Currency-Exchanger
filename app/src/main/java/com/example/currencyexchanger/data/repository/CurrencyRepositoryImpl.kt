package com.example.currencyexchanger.data.repository

import com.example.currencyexchanger.data.api.ApiService
import com.example.currencyexchanger.domain.converter.toDomain
import com.example.currencyexchanger.domain.model.ExchangeRate
import com.example.currencyexchanger.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    CurrencyRepository {

    override suspend fun getLatestCurrencies(): ExchangeRate {
        return apiService.fetchRates().toDomain()
    }
}
