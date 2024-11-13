package com.example.privat



data class ExchangeRateResponse(
    val date: String,
    val bank: String,
    val baseCurrency: Int,
    val baseCurrencyLit: String,
    val exchangeRate: List<ExchangeRate>
)

data class ExchangeRate(
    val currency: String?,
    val baseCurrency: String?,
    val saleRateNB: Double?,
    val purchaseRateNB: Double?,
    val saleRate: Double?,
    val purchaseRate: Double?
)