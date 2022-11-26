package com.example.vikiassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vikiassignment.model.ApiInterface
import com.example.vikiassignment.model.LatestRates
import com.example.vikiassignment.model.PairAmount
import com.example.vikiassignment.model.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository): ViewModel() {
    private val latestRatesLiveData = MutableLiveData<LatestRates>()
    private val pairRateLiveData = MutableLiveData<PairAmount>()

    var fromCurrencyPosition = 0
    var toCurrencyPosition = 1
    var fromCurrencyValue = ""
    var toCurrencyValue = ""

    fun observeLatestRatesLiveData(): LiveData<LatestRates> = latestRatesLiveData
    fun observePairRateLiveData(): LiveData<PairAmount> = pairRateLiveData

    fun getLatestRates() {
        mainRepository.getLatestRates(latestRatesLiveData)
    }

    fun getPairAmount(fromCurrency: String, toCurrency: String, fromInput: String) {
        mainRepository.getPairAmount(pairRateLiveData, fromCurrency, toCurrency, fromInput)
    }
}