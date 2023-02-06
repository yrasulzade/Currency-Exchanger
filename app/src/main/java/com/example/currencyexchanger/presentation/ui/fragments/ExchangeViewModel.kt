package com.example.currencyexchanger.presentation.ui.fragments

import com.example.currencyexchanger.domain.model.CurrencyRate
import com.example.currencyexchanger.domain.useCase.CalculateFeeUseCase
import com.example.currencyexchanger.domain.useCase.ConvertUseCase
import com.example.currencyexchanger.domain.useCase.ExchangeRateUseCase
import com.example.currencyexchanger.presentation.base.BaseViewModel
import com.example.currencyexchanger.presentation.extensions.formatFourDecimal
import com.example.currencyexchanger.presentation.extensions.formatTwoDecimal
import com.example.currencyexchanger.presentation.util.BASE_CURRENCY
import com.example.currencyexchanger.presentation.util.FETCH_EXCHANGE_LIST_INTERVAL
import com.example.currencyexchanger.presentation.util.USD
import com.example.currencyexchanger.presentation.util.Utils.ifNonNull
import com.example.currencyexchanger.presentation.util.Utils.ifNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeRateUseCase: ExchangeRateUseCase,
    private val convertUseCase: ConvertUseCase,
    private val calculateFeeUseCase: CalculateFeeUseCase
) : BaseViewModel<ExchangeState>() {

    var sellCurrency = BASE_CURRENCY
    var buyCurrency = USD
    private var transactionAmount = 0
    private var convertedAmount = 0.0

    private val balanceCurrencyList = HashMap<String, Double>()
    private var exchangeRatesNewList = mutableListOf<CurrencyRate>()

    init {
        balanceCurrencyList[BASE_CURRENCY] = 1000.00
        postState(ExchangeState.BalanceList(balanceCurrencyList))

        fetchExchangeRates()
    }

    fun convert(amount: String) {
        if (amount.isEmpty()) {
            postState(ExchangeState.DisableButton)
            return
        }
        val origin = findCurrencyRate(BASE_CURRENCY)
        val sellCurrency = findCurrencyRate(sellCurrency)
        val buyCurrency = findCurrencyRate(buyCurrency)

        val sellCurrencyInOriginCurrency = convertUseCase.invoke(
            ConvertUseCase.Params(
                sellCurrency.rate, origin.rate, amount.toDouble()
            )
        )

        convertedAmount = convertUseCase.invoke(
            ConvertUseCase.Params(
                origin.rate, buyCurrency.rate, sellCurrencyInOriginCurrency
            )
        )

        postState(ExchangeState.ConversionResult(convertedAmount))
    }

    fun submitButtonClick(amount: Double) {
        val buyCurrencyBalance = balanceCurrencyList[buyCurrency]
        val sellCurrencyBalance = balanceCurrencyList[sellCurrency]
        val fee = calculateFeeUseCase.invoke(
            CalculateFeeUseCase.Params(
                amount, transactionAmount
            )
        )

        sellCurrencyBalance?.let { nonNullSellCurrencyBalance ->

            val balance = (nonNullSellCurrencyBalance - (amount + fee))

            if (balance < 0) {
                postState(ExchangeState.NotEnoughBalance)
                return
            }

            buyCurrencyBalance.ifNull {
                balanceCurrencyList[buyCurrency] = convertedAmount - fee
            }.ifNonNull {
                balanceCurrencyList[buyCurrency] = (it + convertedAmount) - fee
            }

            balanceCurrencyList[sellCurrency] = balance

            postState(
                ExchangeState.OnCurrencyConverted(
                    amount.formatTwoDecimal(),
                    sellCurrency,
                    convertedAmount.formatTwoDecimal(),
                    buyCurrency,
                    fee.formatFourDecimal()
                )
            )

            postState(ExchangeState.BalanceList(balanceCurrencyList))
            transactionAmount++
        }
    }

    private fun fetchExchangeRates() {
        launchAll {
            while (currentCoroutineContext().isActive) {

                exchangeRateUseCase.launch(Unit) {
                    onSuccess = {
                        exchangeRatesNewList.clear()
                        exchangeRatesNewList.addAll(it.rates)
                        postState(ExchangeState.ExchangeListState(it.rates))
                    }
                }
                delay(FETCH_EXCHANGE_LIST_INTERVAL)
            }
        }
    }

    private fun findCurrencyRate(currency: String): CurrencyRate {
        return exchangeRatesNewList.first { rate ->
            rate.currencyId == currency
        }
    }

    fun getBalanceCurrencyList(): List<String> {
        return balanceCurrencyList.keys.toList()
    }

    fun getExchangeCurrencyList(): List<String> {
        return exchangeRatesNewList.map { it.currencyId }
    }
}
