package com.example.praktikumtam_2417051001.uii.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(email: String, onNavigateBack: () -> Unit, onLogout: () -> Unit) {
    var nama by remember { mutableStateOf("Chayya") }

    var currentEmail by remember { mutableStateOf(if (email.isNotBlank()) email else "chayya@email.com") }

    var isEditing by remember { mutableStateOf(false) }
    var notifikasiAktif by remember { mutableStateOf(true) }

    var tempNama by remember { mutableStateOf(nama) }
    var tempEmail by remember { mutableStateOf(currentEmail) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Profil" else "Profil") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isEditing) isEditing = false else onNavigateBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(60.dp), tint = Color.Gray)
            }
            Spacer(modifier = Modifier.height(24.dp))

            if (isEditing) {
                OutlinedTextField(
                    value = tempNama,
                    onValueChange = { tempNama = it },
                    label = { Text("Nama Lengkap") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = tempEmail,
                    onValueChange = { tempEmail = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedButton(
                        onClick = { isEditing = false },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Batal")
                    }
                    Button(
                        onClick = {
                            nama = tempNama
                            currentEmail = tempEmail
                            isEditing = false
                        },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Simpan")
                    }
                }
            } else {
                Text(nama, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(currentEmail, color = Color.Gray, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        tempNama = nama
                        tempEmail = currentEmail
                        isEditing = true
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Edit Profil")
                }

                Spacer(modifier = Modifier.height(32.dp))
                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Pengaturan",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.Gray)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Pengingat Aktivitas", style = MaterialTheme.typography.bodyLarge)
                    }
                    Switch(
                        checked = notifikasiAktif,
                        onCheckedChange = { notifikasiAktif = it }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Keluar")
                }
            }
        }
    }
}