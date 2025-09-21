package com.example.myapplication.viewPages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.components.HomeHeader
import com.example.myapplication.components.Shopping

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        Spacer(modifier.height(15.dp))
        HomeHeader()
        Text("Categorias",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp)
        Shopping()
    }
}