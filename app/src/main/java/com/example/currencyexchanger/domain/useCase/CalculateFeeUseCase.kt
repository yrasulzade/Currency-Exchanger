package com.example.currencyexchanger.domain.useCase

import com.example.currencyexchanger.presentation.base.BaseUseCase
import com.example.currencyexchanger.presentation.util.FEE_AMOUNT
import com.example.currencyexchanger.presentation.util.MIN_FREE_CONVERSION
import javax.inject.Inject

class CalculateFeeUseCase @Inject constructor() :
    BaseUseCase<CalculateFeeUseCase.Params, Double>() {

    override fun execute(parameter: Params): Double {
        return if (parameter.transactionAmount >= MIN_FREE_CONVERSION) {
            return parameter.amount * FEE_AMOUNT
        } else 0.0
    }

    data class Params(val amount: Double, val transactionAmount: Int)

}
