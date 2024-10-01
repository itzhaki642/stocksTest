package com.fcbox.stock

import androidx.lifecycle.viewModelScope
import com.fcbox.stock.model.StockDTO
import com.fcbox.stock.model.StockDetailResponse
import com.movies.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
open class StockDetailsViewModel @Inject constructor(
    private val mStockDetailsRepository: StockDetailsRepository
) : BaseViewModel() {

    val mStockDetailsLiveData = mStockDetailsRepository.mStockDetailsLiveData
    fun getStockDetails(id: String) {
        viewModelScope.launch {
            mStockDetailsRepository.getStockDetailsById(id)

        }
    }



}

