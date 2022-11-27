package com.example.vikiassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vikiassignment.model.LatestRates
import com.example.vikiassignment.model.PairAmount
import com.example.vikiassignment.model.repository.MainRepository
import com.example.vikiassignment.utils.DispatcherProvider
import com.example.vikiassignment.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val mainRepository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
    ): ViewModel() {
    val latestRatesLiveData = MutableLiveData<Resource<LatestRates>>()
    val pairRateLiveData = MutableLiveData<Resource<PairAmount>>()

    var fromCurrencyPosition = 0
    var toCurrencyPosition = 1
    var fromCurrencyValue = ""
    var toCurrencyValue = ""

    fun observeLatestRatesLiveData(): LiveData<Resource<LatestRates>> = latestRatesLiveData
    fun observePairRateLiveData(): LiveData<Resource<PairAmount>> = pairRateLiveData

    fun getLatestRates() {
        viewModelScope.launch(dispatcherProvider.io) {
            val response = mainRepository.getLatestRates()
            withContext(dispatcherProvider.main) {
                if (response is Resource.Success) {
                    latestRatesLiveData.value = Resource.Success(response.data!!)
                } else {
                    latestRatesLiveData.value = Resource.Error(response.message!!)
                }
            }
        }
    }

    fun convertAmount(fromCurrency: String, toCurrency: String, fromInput: String){
        if (fromCurrency === toCurrency) {
            pairRateLiveData.value = Resource.Error("From and to values are same.")
            return
        } else if (fromInput.isEmpty()) {
            pairRateLiveData.value = Resource.Error("Please enter a value to convert.")
            return
        } else {
            viewModelScope.launch(dispatcherProvider.io) {
                val response = mainRepository.getPairAmount(fromCurrency, toCurrency, fromInput)
                withContext(dispatcherProvider.main) {
                    if (response is Resource.Success) {
                        pairRateLiveData.value = Resource.Success(response.data!!)
                    } else {
                        pairRateLiveData.value = Resource.Error(response.message!!)
                    }
                }
            }
        }
    }
}