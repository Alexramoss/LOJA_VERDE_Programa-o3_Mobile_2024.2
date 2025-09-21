package com.example.myapplication.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.model.shopCategoriesModel
import com.example.myapplication.view.CategoryActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun Shopping(modifier: Modifier = Modifier) {
    val shopping = remember { mutableStateOf<List<shopCategoriesModel>>(emptyList())}

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data").document("shop").collection("categories")
            .get().addOnCompleteListener() {
                if (it.isSuccessful) {
                    val result = it.result.documents.mapNotNull { unit ->
                        unit.toObject(shopCategoriesModel::class.java) }
                    shopping.value = result
                }
            }
    }
    LazyColumn() {
        items(shopping.value){ item ->
            CategoryOption(item)
        }
    }
}


@Composable
fun CategoryOption(category: shopCategoriesModel) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(24.dp),
        onClick = {
            context.startActivity(
                Intent(context, CategoryActivity::class.java)
                    .putExtra("category", category.id)
            )
        },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = category.imageURL,
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xAA000000)),
                            startY = 100f
                        )
                    )
            )

            Text(
                text = category.name,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 22.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}

