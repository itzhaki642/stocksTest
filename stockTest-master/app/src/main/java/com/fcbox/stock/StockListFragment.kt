package com.fcbox.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.navigation.findNavController
import com.fcbox.stock.base.BaseFragment
import com.fcbox.stock.model.StockDTO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockListFragment : BaseFragment<StockListViewModel>() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SetupView()
            }
        }
    }

    @Composable
    fun SetupView() {
        var stockList: List<StockDTO> by rememberSaveable { mutableStateOf(emptyList()) }
        var isLoading: Boolean by rememberSaveable { mutableStateOf(false) }

        when (val viewState = mViewModel.stockListViewState.observeAsState().value) {
            is StockListViewModel.StockListViewState.Error -> Toast.makeText(
                context, stringResource(
                    R.string.error, viewState.errorCode
                ), Toast.LENGTH_SHORT
            ).show()

            is StockListViewModel.StockListViewState.Loading -> isLoading = viewState.isLoading
            is StockListViewModel.StockListViewState.StockList -> {
                isLoading = false
                stockList = viewState.data
            }

            null -> {}

        }

        StockListView(
            isLoading = isLoading,
            stockList = stockList,
            onStockClicked = {
                val bundle = Bundle()
                bundle.putString("id", it)
                view?.findNavController()?.navigate(R.id.action_stockList_to_stockDetails,bundle)

            }
        )
        
    }

    override fun getViewModel(): Class<StockListViewModel> = StockListViewModel::class.java

}
