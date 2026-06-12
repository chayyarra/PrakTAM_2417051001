package com.example.praktikumtam_2417051001.uii.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.praktikumtam_2417051001.ui.theme.CoralPrimary
import com.example.praktikumtam_2417051001.ui.theme.TextDark

@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit, onLoginSuccess: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to MicroHabit!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            color = CoralPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Masuk untuk mulai habit cepatmu hari ini.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextDark
        )
        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isError = false
            },
            label = { Text("Email") },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isError = false
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        if (isError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    isError = true
                    errorMessage = "Email dan Password tidak boleh kosong!"
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    isError = true
                    errorMessage = "Format email tidak valid!"
                } else if (password.length < 6) {
                    isError = true
                    errorMessage = "Password minimal 6 karakter!"
                } else {
                    onLoginSuccess(email)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Masuk Sekarang", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onNavigateToRegister) {
            Text("Belum punya akun? Buat di sini.", color = CoralPrimary, fontWeight = FontWeight.Bold)
        }
    }
}