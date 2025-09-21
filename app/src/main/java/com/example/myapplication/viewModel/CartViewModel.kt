package com.example.myapplication.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.ProductsModel
import com.example.myapplication.model.itemModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class CartViewModel: ViewModel() {

    var products = mutableStateOf<List<ProductsModel>>(emptyList())
    private set

    fun addToCart(productID: String){
        val item = itemModel(productID, 1, false)
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance()
                .currentUser?.uid.toString())
            .collection("cart").document(productID).set(item)
    }

    fun removeFromCart(productID: String){
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance()
                .currentUser?.uid.toString())
            .collection("cart")
            .document(productID)
            .delete()
            fetchCartItems()
    }

    fun getChecked(productID: String, onResult: (Boolean) -> Unit ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        Firebase.firestore.collection("users")
            .document(uid)
            .collection("cart")
            .document(productID)
            .get()
            .addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    val document = it.result
                    if (document.exists()) {
                        val state = document.get("checked")
                        onResult(state.toString().toBoolean())
                    }
                }
            }
    }

    fun getQuantity(productID: String, onResult: (Int) -> Unit ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        Firebase.firestore.collection("users")
            .document(uid)
            .collection("cart")
            .document(productID)
            .get()
            .addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    val document = it.result
                    if (document.exists()) {
                        val quantity = document.getLong("quantity") ?: 0
                        onResult(quantity.toInt())
                    }
                }
            }
    }

    fun checkProduct(productID: String, state: Boolean){
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance()
                .currentUser?.uid.toString())
            .collection("cart")
            .document(productID)
            .update("checked", state)
    }

    fun increaseQuantity(productID: String){
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance()
                .currentUser?.uid.toString())
            .collection("cart")
            .document(productID)
            .update("quantity", FieldValue.increment(1))
    }

    fun decreaseQuantity(productID: String) {
        getQuantity(productID) { quantity ->
            if (quantity > 1) {
                Firebase.firestore.collection("users")
                    .document(
                        FirebaseAuth.getInstance()
                            .currentUser?.uid.toString()
                    )
                    .collection("cart")
                    .document(productID)
                    .update("quantity", FieldValue.increment(-1))
            }
        }
    }

    fun cleanCart() {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance()
            .currentUser?.uid.toString())
            .collection("cart")
            .get()
            .addOnSuccessListener { result ->
            for (document in result) {
                Firebase.firestore.collection("users")
                    .document(FirebaseAuth.getInstance()
                    .currentUser?.uid.toString())
                    .collection("cart")
                    .document(document.id)
                    .delete()
                fetchCartItems()
            }
        }
    }

    fun fetchCartItems() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val db = Firebase.firestore
        Log.d("CARTVIEWMODEL", uid)
        db.collection("users")
            .document(uid)
            .collection("cart")
            .get()
            .addOnCompleteListener() {
                if (it.isSuccessful) {
                    val items = it.result.documents.mapNotNull { unit ->
                        unit.toObject(itemModel::class.java)
                    }
                    Log.d("CARTVIEWMODEL", items.toString())
                    if (items.toString() != "[]") {
                        Log.d("CARTVIEWMODEL", items.toString())
                        val productList = mutableListOf<ProductsModel>()
                        var count = 0

                        items.forEach { item ->
                            db.collection("data")
                                .document("shop")
                                .collection("products")
                                .whereEqualTo("id", item.id)
                                .get()
                                .addOnSuccessListener { prodSnapshot ->
                                    val productsFound = prodSnapshot.documents.mapNotNull {
                                        it.toObject(ProductsModel::class.java)
                                    }
                                    productList.addAll(productsFound)
                                    count++
                                    if (count == items.size) {
                                        products.value = productList
                                    }
                                }
                        }
                    } else {
                        products = mutableStateOf<List<ProductsModel>>(emptyList())
                    }
                }
            }
    }
}

