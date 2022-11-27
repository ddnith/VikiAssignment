package com.example.vikiassignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.vikiassignment.model.repository.FakeRepository
import com.example.vikiassignment.utils.FakeDispatcherProvider
import com.example.vikiassignment.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetLatestRatesSuccess() = runTest{
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        viewModel = MainViewModel(FakeRepository(), FakeDispatcherProvider(testDispatcher, testDispatcher, testDispatcher, testDispatcher))
        viewModel.getLatestRates()
        val value = viewModel.latestRatesLiveData.getOrAwaitValue()
        assertThat(value.data!!.timeNextUpdateUnix).isEqualTo(1)
        assertThat(value.message).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetLatestRatesError() = runTest{
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val fakeRepository = FakeRepository()
        fakeRepository.shouldReturnNetworkError = true
        viewModel = MainViewModel(fakeRepository, FakeDispatcherProvider(testDispatcher, testDispatcher, testDispatcher, testDispatcher))
        viewModel.getLatestRates()
        val value = viewModel.latestRatesLiveData.getOrAwaitValue()
        assertThat(value.data).isNull()
        assertThat(value.message).isNotEmpty()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testConvertAmountSuccess() = runTest{
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        viewModel = MainViewModel(FakeRepository(), FakeDispatcherProvider(testDispatcher, testDispatcher, testDispatcher, testDispatcher))
        viewModel.convertAmount("USD","SGD","1")
        val value = viewModel.pairRateLiveData.getOrAwaitValue()
        assertThat(value.data!!.timeNextUpdateUnix).isEqualTo(1)
        assertThat(value.message).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testConvertAmountError() = runTest{
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val fakeRepository = FakeRepository()
        fakeRepository.shouldReturnNetworkError = true
        viewModel = MainViewModel(fakeRepository, FakeDispatcherProvider(testDispatcher, testDispatcher, testDispatcher, testDispatcher))
        viewModel.getLatestRates()
        val value = viewModel.pairRateLiveData.getOrAwaitValue()
        assertThat(value.data).isNull()
        assertThat(value.message).isNotEmpty()
    }
}