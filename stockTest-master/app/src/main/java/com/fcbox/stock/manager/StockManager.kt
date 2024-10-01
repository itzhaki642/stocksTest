package com.fcbox.stock.manager

import androidx.lifecycle.MutableLiveData
import com.fcbox.stock.StockListViewModel
import com.fcbox.stock.api.StockService
import com.fcbox.stock.model.FeedDetailDTO
import com.fcbox.stock.model.FeedListResponse
import com.fcbox.stock.model.StockDTO
import com.fcbox.stock.model.StockDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class StockManager @Inject constructor(private val stockService: StockService) {


    val mStockListViewState = MutableLiveData<StockListViewModel.StockListViewState>()
    var mListOfStock = mutableListOf<StockDetailResponse>()
    private var mGetLastPriceJob: Job? = null
    private var mHistoryPriceJob: Job? = null

    var mListOfStockWithPrice = mutableListOf<StockDTO>()
    val mStockDetailsLiveData = MutableLiveData<StockDTO>()


    fun fetchStockList() {
        stockService.getStockList().enqueue(object : Callback<List<StockDetailResponse>> {
            override fun onFailure(call: Call<List<StockDetailResponse>>, t: Throwable) {
                mStockListViewState.postValue(
                    StockListViewModel.StockListViewState.Error("Something went Wrong")
                )
            }

            override fun onResponse(
                call: Call<List<StockDetailResponse>>,
                response: Response<List<StockDetailResponse>>
            ) {
                response.body()?.let {
                    startFetchFeed()
                    mListOfStock = it.toMutableList()
                }


            }
        })
    }

    private fun fetchFeedList() {
        stockService.getCurrentPriceList().enqueue(object : Callback<FeedListResponse> {
            override fun onFailure(call: Call<FeedListResponse>, t: Throwable) {
                StockListViewModel.StockListViewState.Error("Something went Wrong")

            }

            override fun onResponse(
                call: Call<FeedListResponse>,
                response: Response<FeedListResponse>
            ) {
                generateStockList(response.body()?.feeds)
            }
        })
    }


    /**
     * take two api response and generate stock list
     */
    private fun generateStockList(feeds: List<FeedDetailDTO>?) {
        val newList = mutableListOf<StockDTO>()

        //create new list if not exist
        if (mListOfStockWithPrice.isEmpty()) {
            mListOfStockWithPrice = mListOfStock.mapNotNull { stock ->
                val price = feeds?.find { it.stockId == stock.id }
                if (price != null) {
                    StockDTO(
                        id = stock.id,
                        name = stock.name,
                        buyPrice = price.buyPrice,
                        sellPrice = price.sellPrice,
                        symbol = stock.symbol,
                        historyPriceList = mutableListOf(Pair(price.buyPrice, price.sellPrice)),
                    )
                } else {
                    null
                }
            }.toMutableList()
        } else {
            //only update details
            mListOfStockWithPrice.forEach { stockWithPrice ->
                val updatedPrice = feeds?.find { it.stockId == stockWithPrice.id }
                if (updatedPrice != null) {
                    stockWithPrice.updatePrices(updatedPrice.buyPrice, updatedPrice.sellPrice)
                }

                // I know it's unnecessary to create a new object here.
                // But if I use the existing object, there is a bug in the fragment that observeAsState
                // stops receiving events. If I had enough time I would research the issue
                val newStock = StockDTO(
                    id = stockWithPrice.id,
                    name = stockWithPrice.name,
                    buyPrice = stockWithPrice.buyPrice,
                    sellPrice = stockWithPrice.sellPrice,
                    symbol = stockWithPrice.symbol,
                    historyPriceList = stockWithPrice.historyPriceList
                )
                newList.add(newStock)
            }
        }
        mStockListViewState.postValue(StockListViewModel.StockListViewState.StockList(newList))
    }


    /**
     * start fetch feed list job
     **/
    fun startFetchFeed() {
        CoroutineScope(Dispatchers.IO).launch {
            mGetLastPriceJob?.cancel()
            mGetLastPriceJob = getLastPriceJob()
        }
    }

    /**
     * start fetch history details job
     **/
    private suspend fun startFetchHistory(id: String) {
        mHistoryPriceJob?.cancel()
        mHistoryPriceJob = getStockDetailsByIdJob(id)

    }

    suspend fun getStockDetailsById(id: String) {
        startFetchHistory(id)
    }

    private suspend fun getStockDetailsByIdJob(id: String): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {

                val newStock = mListOfStockWithPrice.find { it.id.toString() == id }
                newStock?.let {
                    // I know it's unnecessary to create a new object here.
                    // But if I use the existing object, there is a bug in the fragment that observeAsState
                    // stops receiving events. If I had enough time I would research the issue
                    mStockDetailsLiveData.postValue(
                        StockDTO(
                            id = it.id,
                            name = it.name,
                            buyPrice = it.buyPrice,
                            sellPrice = it.sellPrice,
                            symbol = it.symbol,
                            historyPriceList = it.historyPriceList.reversed().toMutableList()
                        )
                    )
                }
                delay(1000)
            }
        }
    }

    private suspend fun getLastPriceJob(): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                fetchFeedList()
                delay(1000)
            }
        }
    }
}