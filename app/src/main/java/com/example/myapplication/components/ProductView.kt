package com.example.myapplication.components

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.myapplication.model.ProductsModel
import com.example.myapplication.viewModel.CartViewModel
import com.example.myapplication.viewPages.ProductsDetails


@Composable
fun ProductView(
    modifier: Modifier = Modifier,
    product: ProductsModel,
    cartViewModel: CartViewModel = viewModel()
) {
    val context = LocalContext.current
    var detailsView by remember { mutableStateOf(false) }
    var isAddingToCart by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(8.dp)
            .height(250.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(4.dp)
        ) {
            AsyncImage(
                model = product.imageURL.firstOrNull(),
                contentDescription = product.description,
                modifier = modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )
            Text(
                text = product.name,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "De: R$ ${product.price}",
                    modifier = modifier.padding(2.dp),
                    fontSize = 8.sp
                )
                Text(
                    text = "Para: R$ ${product.newPrice}",
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    modifier = modifier.padding(1.dp),
                    fontSize = 10.sp
                )
            }
            Row {
                OutlinedButton(onClick = { detailsView = true }) {
                    Text("Detalhes", maxLines = 1)
                }

                Button(
                    onClick = {
                        isAddingToCart = true
                        cartViewModel.addToCart(product.id)
                        // Simula um delay leve — pode substituir por retorno de callback se tiver
                        Handler(Looper.getMainLooper()).postDelayed({
                            isAddingToCart = false
                            Toast.makeText(context, "${product.name} adicionado ao carrinho", Toast.LENGTH_SHORT).show()
                        }, 1000)
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    if (isAddingToCart) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier
                                .height(16.dp)
                                .padding(end = 4.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Adicionar no Carrinho"
                        )
                    }
                }
            }
        }
    }

    if (detailsView) {
        val auxmodifier = Modifier
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = auxmodifier.padding(16.dp)
        ) {
            ProductsDetails(modifier, product)
            Column {
                var isAddingInDetails by remember { mutableStateOf(false) }

                Button(
                    onClick = {
                        isAddingInDetails = true
                        cartViewModel.addToCart(product.id)
                        Handler(Looper.getMainLooper()).postDelayed({
                            isAddingInDetails = false
                            Toast.makeText(context, "${product.name} adicionado ao carrinho", Toast.LENGTH_SHORT).show()
                        }, 1000)
                    },
                    modifier = auxmodifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    if (isAddingInDetails) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier
                                .height(20.dp)
                                .padding(end = 8.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Adicionar no Carrinho"
                        )
                    }
                }

                Spacer(modifier = auxmodifier.height(10.dp))

                OutlinedButton(
                    onClick = { detailsView = false },
                    modifier = auxmodifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text("Fechar Detalhes")
                }
            }
        }
    }
}
