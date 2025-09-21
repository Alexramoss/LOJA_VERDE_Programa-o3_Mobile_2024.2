package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.orderModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class OrderViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun addOrder(id: String, total: String) {
        val name = "Minha Compra"
        db.collection("users")
            .document(uid)
            .collection("orders")
            .add(orderModel(id, name, total))
    }

    fun getOrder(onResult: (List<orderModel>) -> Unit) {
        db.collection("users")
            .document(uid)
            .collection("orders")
            .get()
            .addOnSuccessListener { result ->
                val orders = result.documents.mapNotNull { doc ->
                    doc.toObject(orderModel::class.java)
                }
                onResult(orders)
            }

    }

}