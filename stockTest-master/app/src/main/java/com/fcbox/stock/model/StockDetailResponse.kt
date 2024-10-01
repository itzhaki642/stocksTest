package com.fcbox.stock.model

import com.google.gson.annotations.SerializedName

data class StockDetailResponse(
    @SerializedName("Id") val id: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Symbol") val symbol: String,
    @SerializedName("PrecisionDigit") val precisionDigit: Int
) {

    override fun toString(): String {
        return "StockDetailDTO(id=$id, name='$name', symbol='$symbol', precisionDigit=$precisionDigit)"
    }
}
