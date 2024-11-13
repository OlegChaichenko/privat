package com.example.privat

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PrivatBankApi {
    @GET("p24api/exchange_rates")
    fun getExchangeRates(
        @Query("date") date: String
    ): Call<ExchangeRateResponse>
}