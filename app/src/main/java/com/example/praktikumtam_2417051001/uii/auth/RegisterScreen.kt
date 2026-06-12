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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onNavigateBack: () -> Unit, onRegisterSuccess: () -> Unit) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .statusBarsPadding() // FIX: UI tidak nabrak status bar atas
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Buat Akun Baru",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = CoralPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Langkah awal untuk konsisten tiap hari.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it; isError = false },
                label = { Text("Nama Lengkap") },
                isError = isError,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; isError = false },
                label = { Text("Email") },
                isError = isError,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; isError = false },
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
                    if (nama.isBlank() || email.isBlank() || password.isBlank()) {
                        isError = true
                        errorMessage = "Semua kolom pendaftaran wajib diisi!"
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        isError = true
                        errorMessage = "Format penulisan email salah!"
                    } else if (password.length < 6) {
                        isError = true
                        errorMessage = "Password minimal terdiri dari 6 karakter!"
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Akun berhasil dibuat! Silakan masuk.")
                            delay(1000)
                            onRegisterSuccess()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Daftar Sekarang", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onNavigateBack) {
                Text("Sudah punya akun? Masuk di sini.", color = CoralPrimary, fontWeight = FontWeight.Bold)
            }
        }
    }
}