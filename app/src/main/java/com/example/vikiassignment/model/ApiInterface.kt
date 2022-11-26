package com.example.vikiassignment.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("latest/{countryCode}")
    suspend fun getLatest(@Path("countryCode") countryCode: String) : Response<LatestRates>

    @GET("pair/{base_code}/{target_code}/{amount}")
    suspend fun getPairAmount(
        @Path("base_code") baseCode: String,
        @Path("target_code") targetCode: String,
        @Path("amount") amount: String
    ) : Response<PairAmount>
}