package com.example.myapplication.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.util.NavigationHelper
import com.example.myapplication.R.*
import com.example.myapplication.viewModel.AuthViewModel


class AuthActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuthScreen()
        }
    }

    @Composable
    fun AuthScreen(authViewModel: AuthViewModel = viewModel()) {
        var userEmail by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }
        val context = LocalContext.current
        var isLoading by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = drawable.greenbannerremovebg),
                contentDescription = "Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Bem-vindo!",
                fontSize = 28.sp,
                color = Color(0xFF1d3825)
            )

            Text(
                text = "Faça login para continuar",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = userEmail,
                onValueChange = { userEmail = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color(0xFFEDEDED),
                    focusedIndicatorColor = Color(0xFF1d3825),
                    unfocusedIndicatorColor = Color.LightGray,
                    cursorColor = Color(0xFF1d3825),
                    focusedLabelColor = Color(0xFF1d3825),
                    unfocusedLabelColor = Color.DarkGray
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color(0xFFEDEDED),
                    focusedIndicatorColor = Color(0xFF1d3825),
                    unfocusedIndicatorColor = Color.LightGray,
                    cursorColor = Color(0xFF1d3825),
                    focusedLabelColor = Color(0xFF1d3825),
                    unfocusedLabelColor = Color.DarkGray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    isLoading = true
                    authViewModel.signIn(userEmail, userPassword) { state, errorMessage ->
                        isLoading = false
                        if (state) {
                            NavigationHelper.Navegate(context, HomeActivity::class.java)
                            Toast.makeText(context, "Bem-vindo!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, errorMessage ?: "Erro ao entrar", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1d3825))
            ) {
                if (isLoading) {
                    androidx.compose.material3.CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                } else {
                    Text("Entrar", fontSize = 18.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    NavigationHelper.Navegate(context, RegisterActivity::class.java)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF1d3825))
            ) {
                Text("Registrar", fontSize = 18.sp)
            }
        }
    }
}

