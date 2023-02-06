package com.example.currencyexchanger.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.currencyexchanger.databinding.ItemExchangeRateBinding

class ExchangeRatesAdapter(
    private val clickedRate: (String) -> Unit
) :
    ListAdapter<String, ExchangeRatesViewHolder>(ExchangeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRatesViewHolder {

        val binding = ItemExchangeRateBinding.inflate(LayoutInflater.from(parent.context))

        return ExchangeRatesViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ExchangeRatesViewHolder, index: Int) {
        val ratePair = getItem(index)

        holder.binding.apply {
            currencyName.text = ratePair
            item.setOnClickListener {
                clickedRate(ratePair)
            }
        }
    }
}
