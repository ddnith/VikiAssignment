package com.example.vikiassignment.di

import com.example.vikiassignment.model.ApiInterface
import com.example.vikiassignment.model.RetrofitHelper
import com.example.vikiassignment.model.repository.MainRepository
import com.example.vikiassignment.model.repository.MainRepositoryImpl
import com.example.vikiassignment.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        RetrofitHelper.getInstance().create(ApiInterface::class.java)
    }

    single<MainRepository> { MainRepositoryImpl(get()) }

    viewModel() { MainViewModel(get())}
}