package com.example.myapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.myapplication.util.AppUtil
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.R.drawable

@Composable
fun HomeHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Bem-vindo de volta 👋",
            fontSize = 22.sp,
            color = Color(0xFF1d3825),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = AppUtil.getUserName(),
            fontSize = 16.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card para o banner
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Image(
                painter = painterResource(id = drawable.greenbannerremovebg),
                contentDescription = "Banner Promocional",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
