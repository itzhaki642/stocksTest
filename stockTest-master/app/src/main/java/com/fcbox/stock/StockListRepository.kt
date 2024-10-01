package com.fcbox.stock

import com.fcbox.stock.manager.StockManager
import javax.inject.Inject

class StockListRepository @Inject constructor(
    private val stockManager: StockManager
) {
    var stockListViewState = stockManager.mStockListViewState

      fun fetchStockList() {
         stockManager.fetchStockList()
    }



}

