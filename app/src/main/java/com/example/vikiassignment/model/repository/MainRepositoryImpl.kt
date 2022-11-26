package com.example.vikiassignment.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.vikiassignment.model.ApiInterface
import com.example.vikiassignment.model.LatestRates
import com.example.vikiassignment.model.PairAmount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainRepositoryImpl(private val api: ApiInterface): MainRepository {

    override fun getLatestRates(latestRatesLiveData: MutableLiveData<LatestRates>) {
        GlobalScope.launch {
            val result = api.getLatest("SGD")
            val latest = result.body()
            if (latest != null) {
                withContext(Dispatchers.Main) {
                    latestRatesLiveData.value = latest
                }
            }
        }
    }

    override fun getPairAmount(
        pairRateLiveData: MutableLiveData<PairAmount>,
        fromCurrency: String,
        toCurrency: String,
        fromInput: String
    ) {
        GlobalScope.launch {
            val result = api.getPairAmount(fromCurrency, toCurrency, fromInput)
            val pairAmount = result.body()
            if (pairAmount != null) {
                withContext(Dispatchers.Main) {
                    pairRateLiveData.value = pairAmount
                }
            }
        }
    }
}