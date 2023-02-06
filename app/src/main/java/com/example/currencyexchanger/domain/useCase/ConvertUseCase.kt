package com.example.currencyexchanger.domain.useCase

import com.example.currencyexchanger.presentation.base.BaseUseCase
import javax.inject.Inject

class ConvertUseCase @Inject constructor() : BaseUseCase<ConvertUseCase.Params, Double>() {

    override fun execute(parameter: Params): Double {
        return (parameter.destinationCurrencyRate / parameter.originCurrencyRate) * parameter.amount
    }

    data class Params(
        val originCurrencyRate: Double, val destinationCurrencyRate: Double, val amount: Double
    )
}
