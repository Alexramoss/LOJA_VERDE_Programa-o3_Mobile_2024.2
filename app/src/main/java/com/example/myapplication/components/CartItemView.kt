package com.example.myapplication.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.myapplication.model.ProductsModel
import com.example.myapplication.viewModel.CartViewModel

@Composable
fun CartItem(modifier: Modifier = Modifier, product: ProductsModel, cartViewModel: CartViewModel = viewModel()) {

    var qtd by remember { mutableIntStateOf(0) }
    var state by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }

    cartViewModel.getQuantity(product.id) { result  ->
        qtd = result
    }
    cartViewModel.getChecked(product.id) { result  ->
        state = result
    }

    Card(
        modifier
            .padding(1.dp)
            .height(200.dp)
            .fillMaxWidth(),
        RoundedCornerShape(10.dp),
        CardDefaults.cardColors(containerColor = Color.White),
        CardDefaults.cardElevation(8.dp)
    ) {
        Row(
        ) {
            Box(modifier = Modifier.size(145.dp)
                .fillMaxHeight()) {
                AsyncImage(
                    model = product.imageURL.firstOrNull(),
                    contentDescription = product.description,
                    modifier = Modifier.size(100.dp)
                        .align(Alignment.BottomCenter)
                )
                Checkbox(
                    checked = state,
                    onCheckedChange = { state = it; cartViewModel.checkProduct(product.id, it) },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp)
                )
            }
            Column(
            ) {
                Spacer(modifier.height(30.dp))
                Text(
                    text = product.name,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )
                Text(
                    text = "R$ "+product.newPrice.toString()+"\n",
                    fontSize = 15.sp
                )
                Row {
                    Button(onClick = {
                        cartViewModel.decreaseQuantity(product.id)
                        if (qtd>1) {
                            qtd -= 1
                        }
                    }) {
                        Text(
                            text="-",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier.width(10.dp))
                    Column {
                        Text(
                            "QTD",
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = qtd.toString(),
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier.width(10.dp))
                    Button(onClick = {
                        cartViewModel.increaseQuantity(product.id)
                        qtd += 1
                    }) {
                        Text(
                            text="+",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
            IconButton(
                onClick = {
                    cartViewModel.removeFromCart(product.id)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Deletar",
                    modifier = modifier.size(50.dp),
                )
            }

        }
    }
}