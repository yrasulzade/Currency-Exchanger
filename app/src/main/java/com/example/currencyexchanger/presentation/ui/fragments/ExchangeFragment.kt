package com.example.currencyexchanger.presentation.ui.fragments

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyexchanger.R
import com.example.currencyexchanger.databinding.FragmentExchangeBinding
import com.example.currencyexchanger.domain.enums.ExchangeType
import com.example.currencyexchanger.domain.model.CurrencyRate
import com.example.currencyexchanger.presentation.adapter.BalanceAdapter
import com.example.currencyexchanger.presentation.base.BaseFragment
import com.example.currencyexchanger.presentation.extensions.*
import com.example.currencyexchanger.presentation.ui.dialog.ExchangeRateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeFragment : BaseFragment<ExchangeState, ExchangeViewModel, FragmentExchangeBinding>() {
    private lateinit var balanceAdapter: BalanceAdapter
    private var currencyList = ArrayList<CurrencyRate>()

    override fun getViewModelClass() = ExchangeViewModel::class.java

    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentExchangeBinding
        get() = FragmentExchangeBinding::inflate

    override val bindViews: FragmentExchangeBinding.() -> Unit = {
        initClickListener()
    }

    private fun initClickListener() {
        binding.apply {
            sellTextView.setOnClickListener {
                showCurrencyDialog(viewmodel.getBalanceCurrencyList(), ExchangeType.SELL)
            }

            buyTextView.setOnClickListener {
                showCurrencyDialog(viewmodel.getExchangeCurrencyList(), ExchangeType.BUY)
            }

            convert.setOnClickListener {
                viewmodel.submitButtonClick(sellEditText.text.toString().toDouble())
            }

            sellEditText.addTextChangedListener {
                viewmodel.convert(
                    sellEditText.text.toString()
                )
            }
        }
    }

    override fun observeState(state: ExchangeState) {
        when (state) {
            is ExchangeState.BalanceList -> initBalanceRecyclerView(state.balanceList)
            is ExchangeState.ExchangeListState -> updateCurrencyExchangeList(state.currencies)
            is ExchangeState.ConversionResult -> showConversionResult(state.result)
            ExchangeState.DisableButton -> disable()
            ExchangeState.NotEnoughBalance -> toast(getString(R.string.balance_warning))
            is ExchangeState.OnCurrencyConverted -> showAlertDialog(state)
        }
    }

    private fun disable() {
        binding.buyEditText.clearEditText()
        binding.convert.disable()
    }

    private fun showConversionResult(result: Double) {
        binding.buyEditText.setText(result.formatTwoDecimal())
        binding.convert.enable()
    }

    private fun updateCurrencyExchangeList(currencies: List<CurrencyRate>) {
        currencyList.clear()
        currencyList.addAll(currencies)
    }

    private fun initBalanceRecyclerView(balanceMap: HashMap<String, Double>) {
        balanceAdapter = BalanceAdapter(balanceMap, requireContext())

        binding.balanceRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = balanceAdapter
        }

        balanceAdapter.submitList(balanceMap.keys.toList())
    }

    private fun showCurrencyDialog(list: List<String>, type: ExchangeType) {
        val dialog = ExchangeRateDialog(list) {
            if (type == ExchangeType.SELL) {
                binding.sellTextView.text = it
                viewmodel.sellCurrency = it
            } else {
                binding.buyTextView.text = it
                viewmodel.buyCurrency = it
            }
        }
        dialog.show(childFragmentManager, "dialog")
    }

    private fun showAlertDialog(state: ExchangeState.OnCurrencyConverted) {
        AlertDialog.Builder(requireContext()).setTitle(R.string.currency_converted)
            .setPositiveButton(R.string.ok) { _, _ -> }.setMessage(
                getString(
                    R.string.you_converted,
                    state.amount,
                    state.sellCurrency,
                    state.convertedAmount,
                    state.buyCurrency,
                    state.fee,
                    state.sellCurrency
                )
            ).show()
    }
}
