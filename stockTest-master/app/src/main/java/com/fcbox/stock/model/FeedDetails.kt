package com.fcbox.stock.model

import com.google.gson.annotations.SerializedName


data class FeedListResponse(
    @SerializedName("Feeds") val feeds: List<FeedDetailDTO>
)
data class FeedDetailDTO(
    @SerializedName("BuyPrice") val buyPrice: String,
    @SerializedName("SellPrice") val sellPrice: String,
    @SerializedName("StockId") val stockId: Int)

