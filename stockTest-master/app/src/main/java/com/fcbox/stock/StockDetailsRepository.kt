package com.fcbox.stock

import com.fcbox.stock.manager.StockManager
import javax.inject.Inject

class StockDetailsRepository @Inject constructor(
    private val stockManager: StockManager
) {
    var mStockDetailsLiveData = stockManager.mStockDetailsLiveData

    suspend fun getStockDetailsById(id: String) {
         stockManager.getStockDetailsById(id)
    }



}

