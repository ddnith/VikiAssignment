package com.example.vikiassignment.model.repository

import androidx.lifecycle.MutableLiveData
import com.example.vikiassignment.model.LatestRates
import com.example.vikiassignment.model.PairAmount

interface MainRepository {
    suspend fun getLatestRates(): LatestRates?
    suspend fun getPairAmount(
        fromCurrency: String,
        toCurrency: String,
        fromInput: String
    ): PairAmount?
}