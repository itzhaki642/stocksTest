package com.fcbox.stock.api


import com.fcbox.stock.model.FeedListResponse
import com.fcbox.stock.model.StockDetailResponse
import retrofit2.Call
import retrofit2.http.GET

interface StockService {
    @GET("api/stock")
     fun getStockList(): Call<List<StockDetailResponse>>


    @GET("api/Feeds")
    fun getCurrentPriceList(): Call<FeedListResponse>

}