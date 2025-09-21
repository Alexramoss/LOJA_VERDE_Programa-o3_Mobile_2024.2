package com.example.myapplication.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AppUtil {

    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    @Composable
    fun getUserName(): String {
        var userName by remember { mutableStateOf("") }
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get().addOnCompleteListener {
                userName = it.result.get("user").toString().split(" ")[0]
            }
            return(userName)
    }

    @Composable
    fun getUserFullName(): String {
        var userFullName by remember { mutableStateOf("") }
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get().addOnCompleteListener {
                userFullName = it.result.get("user").toString()
            }
        return(userFullName)
    }

    @Composable
    fun getUserEmail(): String {
        var userEmail by remember { mutableStateOf("") }
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get().addOnCompleteListener {
                userEmail = it.result.get("email").toString()
            }
        return(userEmail)
    }
}