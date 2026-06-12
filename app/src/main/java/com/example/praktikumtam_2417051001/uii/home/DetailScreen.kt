package com.example.praktikumtam_2417051001.uii.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.praktikumtam_2417051001.MainViewModel
import com.example.praktikumtam_2417051001.model.ActivityModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(activityData: ActivityModel, mainViewModel: MainViewModel, onNavigateBack: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    var isProcessing by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Aktivitas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = activityData.imageRes,
                contentDescription = null,
                loading = { CircularProgressIndicator() },
                modifier = Modifier.size(240.dp).clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(activityData.nama, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Target: ${activityData.deskripsi}", color = Color.Gray, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    coroutineScope.launch {
                        isProcessing = true
                        delay(1200)
                        mainViewModel.addExp(50)

                        snackbarHostState.showSnackbar("Habit Berhasil Diselesaikan! +50 EXP")
                        isProcessing = false
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = !isProcessing
            ) {
                if (isProcessing) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Selesaikan & Dapatkan EXP", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}