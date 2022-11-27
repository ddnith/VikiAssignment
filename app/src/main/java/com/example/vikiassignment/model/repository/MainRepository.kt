package com.example.vikiassignment.model.repository

import androidx.lifecycle.MutableLiveData
import com.example.vikiassignment.model.LatestRates
import com.example.vikiassignment.model.PairAmount
import com.example.vikiassignment.utils.Resource

interface MainRepository {
    suspend fun getLatestRates(): Resource<LatestRates>
    suspend fun getPairAmount(
        fromCurrency: String,
        toCurrency: String,
        fromInput: String
    ): Resource<PairAmount>
}