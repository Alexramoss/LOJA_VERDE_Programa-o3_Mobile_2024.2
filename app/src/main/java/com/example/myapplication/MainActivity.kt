package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.util.NavigationHelper
import com.example.myapplication.view.AuthActivity
import com.example.myapplication.view.HomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    val context: Context
        @Composable
        get() = LocalContext.current

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            if (Firebase.auth.currentUser != null) {
                NavigationHelper.Navegate(context, HomeActivity::class.java)
            } else {
                NavigationHelper.Navegate(context, AuthActivity::class.java)
            }
        }
    }
}


