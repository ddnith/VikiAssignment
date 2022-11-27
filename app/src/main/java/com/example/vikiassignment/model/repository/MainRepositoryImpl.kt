package com.example.vikiassignment.model.repository

import com.example.vikiassignment.model.ApiInterface
import com.example.vikiassignment.model.LatestRates
import com.example.vikiassignment.model.PairAmount

class MainRepositoryImpl(private val api: ApiInterface): MainRepository {

    override suspend fun getLatestRates(): LatestRates? {
        val result = api.getLatest("SGD")
        return result.body()
    }

    override suspend fun getPairAmount(
        fromCurrency: String,
        toCurrency: String,
        fromInput: String
    ): PairAmount? {
        val result = api.getPairAmount(fromCurrency, toCurrency, fromInput)
        return result.body()
    }
}