package com.example.vikiassignment.model.repository

import com.example.vikiassignment.model.LatestRates
import com.example.vikiassignment.model.PairAmount
import com.example.vikiassignment.utils.Resource

class FakeRepository: MainRepository {

    var shouldReturnNetworkError = false

    override suspend fun getLatestRates(): Resource<LatestRates> {
        return if (!shouldReturnNetworkError) {
            Resource.Success(
                LatestRates("","","",1L,"",1L,"","", mutableMapOf())
            )
        } else {
            Resource.Error("Error")
        }
    }

    override suspend fun getPairAmount(
        fromCurrency: String,
        toCurrency: String,
        fromInput: String
    ): Resource<PairAmount> {
        return if (!shouldReturnNetworkError) {
            Resource.Success(
                PairAmount("","","",1L,"",1L,"","",1.0, 1.0)
            )
        } else {
            Resource.Error("Error")
        }
    }
}