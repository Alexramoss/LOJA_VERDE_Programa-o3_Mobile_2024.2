package com.example.myapplication.viewPages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.model.ProductsModel
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.WormIndicatorType

@Composable
fun ProductsDetails(modifier: Modifier, product: ProductsModel) {
    Column {
        val page = rememberPagerState(0) {
            product.imageURL.size
        }
        HorizontalPager(
            state = page,
            pageSpacing = 30.dp
        ) {
            AsyncImage(
                model = product.imageURL[it],
                contentDescription = product.name,
                modifier = modifier.fillMaxWidth()
                    .height(350.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        DotsIndicator(
            dotCount = product.imageURL.size,
            pagerState = page,
            type = WormIndicatorType(
                dotsGraphic = DotGraphic(color = Color(0xFF1d3825))
            ),
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        Text(
            text = product.name,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "De: R$ ${product.price}",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        Text(
            text = "Por: R$ ${product.newPrice}",
            fontSize = 20.sp,
            color = Color(0xFF1d3825),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = "Descrição do produto",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = product.description,
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

