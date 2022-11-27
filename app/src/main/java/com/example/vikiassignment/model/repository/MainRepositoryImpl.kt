package com.example.vikiassignment.model.repository

import com.example.vikiassignment.model.ApiInterface
import com.example.vikiassignment.model.LatestRates
import com.example.vikiassignment.model.PairAmount
import com.example.vikiassignment.utils.Resource

class MainRepositoryImpl(private val api: ApiInterface): MainRepository {

    override suspend fun getLatestRates(): Resource<LatestRates> {
        return try {
            val response = api.getLatest("SGD")
            val result =  response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: java.lang.Exception) {
            Resource.Error(e.message?:"An error occurred")
        }
    }

    override suspend fun getPairAmount(
        fromCurrency: String,
        toCurrency: String,
        fromInput: String
    ): Resource<PairAmount> {
        return try {
            val response = api.getPairAmount(fromCurrency, toCurrency, fromInput)
            val result =  response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: java.lang.Exception) {
            Resource.Error(e.message?:"An error occurred")
        }
    }
}