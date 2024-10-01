package com.fcbox.stock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.fcbox.stock.model.StockDTO
import com.fcbox.stock.model.StockDetailResponse


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun StockDetailsView(
    stockDetails: StockDTO) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.history_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                items(stockDetails.historyPriceList) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(16.dp)

                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            // Buy Price
                            Text(
                                text = "Buy Price:",
                                modifier = Modifier
                                    .wrapContentSize(),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.design_default_color_on_secondary)
                            )
                            Text(
                                text = item.first,
                                modifier = Modifier
                                    .wrapContentSize(),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                color = colorResource(id = R.color.design_default_color_on_secondary),
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )
                            // Sell Price
                            Text(
                                text = "Sell Price:",
                                modifier = Modifier
                                    .wrapContentSize(),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.design_default_color_on_secondary)
                            )
                            Text(
                                text = item.second,
                                modifier = Modifier
                                    .wrapContentSize(),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                color = colorResource(id = R.color.design_default_color_on_secondary),
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

    }
}

