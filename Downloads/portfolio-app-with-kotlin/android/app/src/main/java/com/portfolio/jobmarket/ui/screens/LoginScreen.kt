package com.portfolio.jobmarket.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.portfolio.jobmarket.ui.AppViewModel
import com.portfolio.jobmarket.ui.theme.NeonBlue
import com.portfolio.jobmarket.ui.theme.NeonPink
import com.portfolio.jobmarket.ui.theme.NeonPurple
import com.portfolio.jobmarket.R

@Composable
fun LoginScreen(
    viewModel: AppViewModel,
    onLoggedIn: () -> Unit,
    onGoToSignup: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "WELCOME",
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(NeonPurple, NeonPink, NeonBlue)
                    ),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email, onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password, onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            error?.let {
                Text(it, color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val res = viewModel.login(email, password)
                    if (res.isSuccess) onLoggedIn()
                    else error = res.exceptionOrNull()?.message ?: "Login failed"
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Sign in") }
            TextButton(onClick = onGoToSignup, modifier = Modifier.fillMaxWidth()) {
                Text("Create an account")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Try ada@example.com / password",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
