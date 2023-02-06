package com.example.currencyexchanger.data.api

import com.example.currencyexchanger.data.api.Endpoints.CONVERT
import com.example.currencyexchanger.data.api.Endpoints.LATEST
import com.example.currencyexchanger.data.model.ConvertResult
import com.example.currencyexchanger.data.model.ExchangeRatesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(LATEST)
    suspend fun fetchRates(): ExchangeRatesDto

    @GET(CONVERT)
    suspend fun convert(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): ConvertResult
}
