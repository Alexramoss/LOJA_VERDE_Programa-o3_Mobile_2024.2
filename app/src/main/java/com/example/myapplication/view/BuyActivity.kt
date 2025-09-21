package com.example.myapplication.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.util.NavigationHelper
import com.example.myapplication.ui.theme.MyAppTheme
import com.example.myapplication.viewModel.CartViewModel
import com.example.myapplication.viewModel.OrderViewModel

class BuyActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UI()
        }
    }

    @SuppressLint("DefaultLocale")
    @Composable
    fun UI(
        cartViewModel: CartViewModel = viewModel(),
        orderViewModel: OrderViewModel = viewModel()
    ) {

        LaunchedEffect(key1 = Unit) {
            cartViewModel.fetchCartItems()
        }

        val products = cartViewModel.products
        val context = LocalContext.current

        var total by remember { mutableFloatStateOf(0f) }
        var endereco by remember { mutableStateOf("") }
        var metodoPagamento by remember { mutableStateOf("Cartão") }

        LaunchedEffect(products.value) {
            var sum = 0f
            products.value.forEach { product ->
                cartViewModel.getQuantity(product.id) { qtd ->
                    cartViewModel.getChecked(product.id) { checked ->
                        if (checked) {
                            val price = product.newPrice.toFloat() * qtd
                            sum += price
                            total = sum
                        }
                    }
                }
            }
        }

        MyAppTheme {
            Column(
                modifier = Modifier.padding(18.dp)
            ) {
                Button(onClick = {
                    NavigationHelper.Navegate(context, HomeActivity::class.java)
                }) {
                    Text("Voltar")
                }

                LazyColumn {
                    items(products.value) { product ->
                        var qtd by remember { mutableIntStateOf(0) }
                        var state by remember { mutableStateOf(false) }

                        cartViewModel.getQuantity(product.id) { qtd = it }
                        cartViewModel.getChecked(product.id) { state = it }

                        if (state) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${qtd}x ${product.name}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                val priceFloat = product.newPrice.toFloat()
                                val priceFinal = priceFloat * qtd

                                Text(
                                    text = "por R$ ${String.format("%.2f", priceFinal)}"
                                )
                            }
                        }
                    }
                }

                Text(
                    text = "Total: R$ ${String.format("%.2f", total)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(Modifier.height(12.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Endereço de Entrega",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = endereco,
                            onValueChange = { endereco = it },
                            label = { Text("Digite seu endereço") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Método de Pagamento",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listOf("Cartão", "Pix", "Dinheiro").forEach { metodo ->
                                Button(
                                    onClick = { metodoPagamento = metodo },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (metodoPagamento == metodo) Color(
                                            0xFF1d3825
                                        ) else Color.LightGray
                                    )
                                ) {
                                    Text(metodo, color = Color.White)
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        orderViewModel.addOrder("1", total.toString())
                        cartViewModel.cleanCart()
                        NavigationHelper.Navegate(context, HomeActivity::class.java)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Finalizar a Compra")
                }
            }
        }
    }
}