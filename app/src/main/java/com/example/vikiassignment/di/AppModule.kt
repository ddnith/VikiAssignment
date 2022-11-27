package com.example.vikiassignment.di

import com.example.vikiassignment.model.ApiInterface
import com.example.vikiassignment.model.repository.MainRepository
import com.example.vikiassignment.model.repository.MainRepositoryImpl
import com.example.vikiassignment.viewmodel.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://v6.exchangerate-api.com/v6/f60614b3836a2d91cea25965/"

val appModule = module {
    val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client : OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }.build()

    single {
        Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .client(client)
            .build()
            .create(ApiInterface::class.java)
    }

    single<MainRepository> { MainRepositoryImpl(get()) }

    viewModel() { MainViewModel(get())}
}