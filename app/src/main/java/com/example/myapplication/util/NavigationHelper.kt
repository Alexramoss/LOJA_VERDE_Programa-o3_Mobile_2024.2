package com.example.myapplication.util

import android.content.Context
import android.content.Intent

object NavigationHelper {
    fun <T> Navegate(context: Context, targetActivity: Class<T>) {
        context.startActivity(Intent(context, targetActivity))
    }
}