package com.example.vikiassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vikiassignment.model.ApiInterface
import com.example.vikiassignment.model.LatestRates
import com.example.vikiassignment.model.PairAmount
import com.example.vikiassignment.model.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        viewModelScope.launch(Dispatchers.IO) {
            val latestRates = mainRepository.getLatestRates()
            latestRates?.let {
                withContext(Dispatchers.Main) {
                    latestRatesLiveData.value = latestRates
                }
            }
        }
    }

    fun getPairAmount(fromCurrency: String, toCurrency: String, fromInput: String){
        viewModelScope.launch(Dispatchers.IO) {
            val data = mainRepository.getPairAmount(fromCurrency, toCurrency, fromInput)
            data?.let {
                withContext(Dispatchers.Main) {
                    pairRateLiveData.value = data
                }
            }
        }
    }
}