package com.example.myapplication.viewPages

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.util.AppUtil
import com.example.myapplication.util.NavigationHelper
import com.example.myapplication.components.ProfilePicture
import com.example.myapplication.view.AuthActivity
import com.example.myapplication.viewModel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ProfilePage(modifier: Modifier = Modifier, authViewModel: AuthViewModel = viewModel()) {

    val context = LocalContext.current
    var userName by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userNewPassword by remember { mutableStateOf("") }
    val userNameDisplay = AppUtil.getUserName()
    var isLoading by remember { mutableStateOf(false) }

    var curretEmail = AppUtil.getUserEmail()


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sair da conta",
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    Firebase.auth.signOut()
                    NavigationHelper.Navegate(context, AuthActivity::class.java)
                }
            )

            Button(
                onClick = {
                    isLoading = true

                    val updates = mutableListOf<() -> Unit>()

                    if (userName.isNotBlank()) {
                        updates.add {
                            authViewModel.changeUser(userName) { state, errorMessage ->
                                if (!state) {
                                    Toast.makeText(
                                        context,
                                        errorMessage ?: "Erro ao atualizar nome",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }

                    if (userPassword.isNotBlank()) {
                        updates.add {
                            authViewModel.changePassword(curretEmail, userPassword, userNewPassword) { state, errorMessage ->
                                if (!state) {
                                    Toast.makeText(
                                        context,
                                        errorMessage ?: "Erro ao atualizar senha",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }

                    updates.forEach { it() }
                    userName = ""


                    Handler(Looper.getMainLooper()).postDelayed({
                        isLoading = false
                        Toast.makeText(context, "Informações salvas com sucesso!", Toast.LENGTH_SHORT).show()
                    }, 1500)
                },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D3825))
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Salvar", fontSize = 14.sp, color = Color.White)
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            ProfilePicture(
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = 8.dp)
            )

            Text(
                text = userNameDisplay,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = AppUtil.getUserEmail(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Thin,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Nome Completo",
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
                    fontSize = 18.sp
                )
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    placeholder = { Text(AppUtil.getUserFullName()) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier.height(15.dp))

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Alterar senha",
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                placeholder = { Text("***********") },
                label = { Text("Senha atual") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            OutlinedTextField(
                value = userNewPassword,
                onValueChange = { userNewPassword = it },
                placeholder = { Text("***********") },
                label = { Text("Nova senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
