package com.example.myapplication.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.util.NavigationHelper
import com.example.myapplication.components.ProductView
import com.example.myapplication.model.ProductsModel
import com.example.myapplication.ui.theme.MyAppTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class CategoryActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val category = intent.getStringExtra("category")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UI(category.toString())
        }
    }

    @Composable
    fun UI(category: String) {
        val context = LocalContext.current
        val products = remember { mutableStateOf<List<ProductsModel>>(emptyList()) }

        LaunchedEffect(key1 = Unit) {
            Firebase.firestore.collection("data").document("shop").collection("products")
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val result = it.result.documents.mapNotNull { unit ->
                            unit.toObject(ProductsModel::class.java)
                        }
                        products.value = result
                    }
                }
        }
        MyAppTheme {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.padding(bottom = 16.dp)) {
                    Button(
                        onClick = {
                            NavigationHelper.Navegate(context, HomeActivity::class.java)
                        }
                    ) {
                        Text(text = "← Início")
                    }
                }

                LazyColumn {
                    items(products.value.chunked(2)) { rowProducts ->
                        Row(modifier = Modifier.padding(bottom = 12.dp)) {
                            rowProducts.forEach {
                                ProductView(
                                    modifier = Modifier
                                        .weight(1f),
                                    product = it
                                )
                            }
                            if (rowProducts.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}