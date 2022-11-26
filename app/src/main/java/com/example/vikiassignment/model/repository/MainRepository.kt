package com.example.vikiassignment.model.repository

import androidx.lifecycle.MutableLiveData
import com.example.vikiassignment.model.LatestRates
import com.example.vikiassignment.model.PairAmount

interface MainRepository {
    fun getLatestRates(latestRatesLiveData: MutableLiveData<LatestRates>)
    fun getPairAmount(
        pairRateLiveData: MutableLiveData<PairAmount>,
        fromCurrency: String,
        toCurrency: String,
        fromInput: String
    )
}