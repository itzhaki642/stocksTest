package com.fcbox.stock.model

import java.io.Serializable


data class StockDTO(
    val id: Int = -1,
    val name: String = "",
    val symbol: String = "",
    val precisionDigit: Int = -1,
    var buyPrice: String = "",
    var sellPrice: String = "",
    val historyPriceList: MutableList<Pair<String, String>> = mutableListOf()
) :Serializable {
    val spread: String
        get() = calculateSpread()

    private fun calculateSpread(): String {
        return try {
            (sellPrice.toDouble() - buyPrice.toDouble()).toString()
        } catch (e: Exception) {
            "Unknown"
        }
    }

    fun updatePrices(newBuyPrice: String, newSellPrice: String) {
        if (historyPriceList.size >= 100) {
            historyPriceList.removeAt(0)
        }
        historyPriceList.add(Pair(newBuyPrice, newSellPrice))
        buyPrice = newBuyPrice
        sellPrice = newSellPrice
    }
}