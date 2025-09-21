package com.example.myapplication.viewPages

import OrderView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.orderModel
import com.example.myapplication.viewModel.OrderViewModel

@Composable
fun HistoryPage(orderViewModel: OrderViewModel = viewModel()) {

    var listOrder by remember { mutableStateOf<List<orderModel>>(emptyList()) }

    LaunchedEffect(key1 = Unit) {
        orderViewModel.getOrder() {
            listOrder = it
        }
    }
    Column {
        Spacer(Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier.padding(4.dp)
        ) {
            items(listOrder) { order ->
                OrderView(order = order)
            }
        }
    }
}