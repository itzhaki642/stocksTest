package com.fcbox.stock

import androidx.lifecycle.asLiveData
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
open class StockListViewModel @Inject constructor(
    private val mStockListRepository: StockListRepository
) : BaseViewModel() {

    init {
        getStockList()
    }

    val stockListViewState = mStockListRepository.stockListViewState

    private fun getStockList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mStockListRepository.fetchStockList()
                stockListViewState.postValue(StockListViewState.Loading(false))
            }
        }
    }

    sealed interface StockListViewState {
        data class Loading(val isLoading: Boolean) : StockListViewState
        data class StockList(val data: List<StockDTO>) : StockListViewState
        data class Error(val errorCode: String) : StockListViewState
    }
}

