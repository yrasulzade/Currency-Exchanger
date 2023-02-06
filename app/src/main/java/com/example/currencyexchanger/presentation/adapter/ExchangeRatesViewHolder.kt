package com.example.currencyexchanger.presentation.adapter

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchanger.databinding.ItemExchangeRateBinding

class ExchangeRatesViewHolder(
    val binding: ItemExchangeRateBinding,
    val context: Context
) : RecyclerView.ViewHolder(binding.root)

object ExchangeDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
