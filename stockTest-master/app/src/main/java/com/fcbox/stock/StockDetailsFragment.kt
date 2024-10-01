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
class StockDetailsFragment : BaseFragment<StockDetailsViewModel>() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getString("id")?.let { id -> mViewModel.getStockDetails(id) }

        }
    }

    @Composable
    fun SetupView() {

        val stockDto by mViewModel.mStockDetailsLiveData.observeAsState()
        stockDto?.let {
            StockDetailsView(
                stockDetails = it
            )
        }
    }

    override fun getViewModel(): Class<StockDetailsViewModel> = StockDetailsViewModel::class.java

}
