package com.fcbox.stock.di

import com.google.gson.Gson
import com.fcbox.stock.api.StockService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {



    @Provides
    @Singleton
    fun providService(client: OkHttpClient): StockService {
        return Retrofit.Builder()
            .baseUrl("http://34.159.211.193/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build()
            .create(StockService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createAuthInterceptor())
            .build()
    }

    private fun createAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
            )
        }
    }
}