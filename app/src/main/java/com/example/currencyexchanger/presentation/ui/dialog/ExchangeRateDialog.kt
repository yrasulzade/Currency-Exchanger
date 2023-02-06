package com.example.currencyexchanger.presentation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyexchanger.R
import com.example.currencyexchanger.databinding.ExchangeRateDialogBinding
import com.example.currencyexchanger.presentation.adapter.ExchangeRatesAdapter

class ExchangeRateDialog(
    private val rates: List<String>,
    private val clickedRate: (String) -> Unit
) : DialogFragment() {
    private lateinit var binding: ExchangeRateDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_DialogOverlay)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExchangeRateDialogBinding.inflate(inflater, container, false)

        initIssueRecyclerView()

        return binding.root
    }

    private fun initIssueRecyclerView() {
        val exchangeRatesAdapter = ExchangeRatesAdapter {
            clickedRate(it)
            dismiss()
        }

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = exchangeRatesAdapter
        }

        exchangeRatesAdapter.submitList(rates)
    }
}
