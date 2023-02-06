package com.example.currencyexchanger.presentation.ui.fragments

import com.example.currencyexchanger.domain.model.CurrencyRate

sealed class ExchangeState {

    data class ExchangeListState(val currencies: List<CurrencyRate>) : ExchangeState()
    data class ConversionResult(val result: Double) : ExchangeState()
    data class BalanceList(val balanceList: HashMap<String, Double>) : ExchangeState()
    object DisableButton : ExchangeState()
    object NotEnoughBalance : ExchangeState()
    data class OnCurrencyConverted(
        val amount: String,
        val sellCurrency: String,
        val convertedAmount: String,
        val buyCurrency: String,
        val fee: String
    ) : ExchangeState()
}
