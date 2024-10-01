package com.fcbox.stock.di


import com.fcbox.stock.manager.StockManager
import com.fcbox.stock.api.StockService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    fun provideStockManager(stockService: StockService) = StockManager(stockService)

}
