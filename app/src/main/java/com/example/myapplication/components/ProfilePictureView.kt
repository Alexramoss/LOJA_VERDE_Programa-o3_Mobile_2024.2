package com.example.myapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R.drawable

@Composable
fun ProfilePicture(modifier: Modifier = Modifier) {


    Card(
        shape = CircleShape,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = drawable.profile_image),
            contentDescription = "profile_image",
            modifier = modifier.fillMaxSize()
        )
        }
}
