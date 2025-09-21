package com.example.myapplication.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.itemModel
import com.example.myapplication.model.userModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth

    private val firestore = Firebase.firestore

    private val cart = itemModel("",0)

    fun register(user: String, email: String,
                 password: String,
                 onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val userUID = it.result.user?.uid
                    val userModel = userModel(user, email, userUID)

                    if (userUID != null) {
                        firestore.collection("users")
                            .document(userUID)
                            .set(userModel)
                            .addOnCompleteListener {
                                dbTask ->
                                if(dbTask.isSuccessful){
                                    Firebase.firestore.collection("users")
                                        .document(FirebaseAuth.getInstance()
                                            .currentUser?.uid.toString())
                                        .collection("cart").document().set(cart)
                                    onResult(true, null)

                                } else {
                                    onResult(false, "A operação falhou!")
                                }
                            }
                    } else {
                        onResult(false, "A operação falhou!")
                    }

                } else {
                    onResult(false, it.exception?.localizedMessage)
                }
        }
    }

    fun signIn(email: String,
               password: String,
               onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                onResult(true, null)
            } else {
                onResult(false, it.exception?.localizedMessage)
            }
        }
    }

    fun changeUserEmail(email: String,
                       onResult: (Boolean, String?) -> Unit
    ) {
        auth.currentUser?.updateEmail(email)?.addOnCompleteListener {
            if (it.isSuccessful) {
                onResult(true, null)
            } else {
                onResult(false, it.exception?.localizedMessage)
            }
        }
    }

    fun changeUser(user: String,
    onResult: (Boolean, String?) -> Unit
    ) {
        firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .update("user", user)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, it.exception?.localizedMessage)
                }
            }
    }

    fun changePassword(email: String, password: String, newPassword: String,
                       onResult: (Boolean, String?) -> Unit
    ) {
        signIn(
            email, password,
        ){state, errorMessage ->
            if (state) {
            auth.currentUser?.updatePassword(newPassword)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, it.exception?.localizedMessage)
                }
            }
        } else {
            onResult(false, errorMessage)
        }
    }

    }
}