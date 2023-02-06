package com.example.currencyexchanger.di

import com.example.currencyexchanger.data.api.ApiService
import com.example.currencyexchanger.data.repository.CurrencyRepositoryImpl
import com.example.currencyexchanger.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDiscoverRepository(
        apiService: ApiService
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(apiService)
    }
}
