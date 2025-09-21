package com.example.myapplication.viewPages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.components.CartItem
import com.example.myapplication.ui.theme.MyAppTheme
import com.example.myapplication.util.NavigationHelper
import com.example.myapplication.view.BuyActivity
import com.example.myapplication.viewModel.CartViewModel

@Composable
fun CartPage(modifier: Modifier = Modifier, cartViewModel: CartViewModel = viewModel()) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        cartViewModel.fetchCartItems()
    }

    val products = cartViewModel.products

    MyAppTheme {
        Column(
            modifier
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Seu Carrinho",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (products.value.isEmpty()) {
                Text(
                    text = "Seu carrinho está vazio 😕",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Medium
                )
            } else {
                Button(
                    onClick = {
                        NavigationHelper.Navegate(context, BuyActivity::class.java)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Comprar Agora!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(products.value) { product ->
                        CartItem(modifier = Modifier.padding(vertical = 8.dp), product)
                    }
                }
                Spacer(Modifier.height(100.dp))
            }
        }
    }
}