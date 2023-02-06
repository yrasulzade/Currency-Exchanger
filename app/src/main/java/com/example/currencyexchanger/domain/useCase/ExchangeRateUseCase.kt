package com.example.currencyexchanger.domain.useCase

import com.example.currencyexchanger.domain.model.ExchangeRate
import com.example.currencyexchanger.domain.repository.CurrencyRepository
import com.example.currencyexchanger.presentation.base.BaseSuspendUseCase
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ExchangeRateUseCase @Inject constructor(
    private val repository: CurrencyRepository, context: CoroutineContext,
) : BaseSuspendUseCase<Unit, ExchangeRate>(context) {

    override suspend fun executeOnBackground(params: Unit): ExchangeRate {
        return repository.getLatestCurrencies()
    }
}
