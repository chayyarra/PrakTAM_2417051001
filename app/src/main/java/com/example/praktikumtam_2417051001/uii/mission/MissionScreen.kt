package com.example.praktikumtam_2417051001.uii.mission

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktikumtam_2417051001.MainViewModel
import com.example.praktikumtam_2417051001.ui.theme.CoralPrimary
import com.example.praktikumtam_2417051001.ui.theme.TextDark
import com.example.praktikumtam_2417051001.ui.theme.WhitePure

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionScreen(mainViewModel: MainViewModel, onNavigateBack: () -> Unit) {
    val exp = mainViewModel.exp.intValue
    val level = mainViewModel.level.intValue
    var showLevelUpText by remember { mutableStateOf(false) }
    var currentLevel by remember { mutableIntStateOf(level) }

    LaunchedEffect(level) {
        if (level > currentLevel) {
            showLevelUpText = true
            kotlinx.coroutines.delay(1200)
            showLevelUpText = false
            currentLevel = level
        }
    }

    val misiList = listOf(
        Pair("Meditasi Pernapasan 5 Menit", 20),
        Pair("Stretching Ringan 10 Menit", 40),
        Pair("Merapikan Meja Belajar", 30),
        Pair("Baca Buku 2 Halaman", 25),
        Pair("Minum Segelas Air Putih", 10)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Misi Harian Cepat", fontWeight = FontWeight.Bold, color = TextDark) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = TextDark) }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(padding)) {
            Column(modifier = Modifier.padding(24.dp)) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CoralPrimary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("LEVEL ANDA", style = MaterialTheme.typography.labelSmall, color = WhitePure.copy(0.7f), fontWeight = FontWeight.ExtraBold)
                        Text("$level", style = MaterialTheme.typography.displayMedium, color = WhitePure, fontWeight = FontWeight.ExtraBold, fontSize = 60.sp)
                        Text("EXP: $exp / ${level * 100}", style = MaterialTheme.typography.titleMedium, color = WhitePure)

                        Spacer(modifier = Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { exp.toFloat() / (level * 100) },
                            modifier = Modifier.fillMaxWidth().height(10.dp).clip(RoundedCornerShape(50)),
                            color = WhitePure,
                            trackColor = WhitePure.copy(0.3f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))
                Text("Tugas Cepat Hari Ini", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(misiList) { misi ->
                        val isClaimed = mainViewModel.claimedMissions.contains(misi.first)

                        MissionItem(judul = misi.first, expReward = misi.second, initiallyClaimed = isClaimed) {
                            mainViewModel.addExp(misi.second)
                            mainViewModel.claimedMissions.add(misi.first)
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = showLevelUpText,
                enter = scaleIn(initialScale = 0.3f) + fadeIn(animationSpec = tween(300)),
                exit = scaleOut(targetScale = 0.3f) + fadeOut(animationSpec = tween(800)),
                modifier = Modifier.align(Alignment.Center).padding(bottom = 80.dp)
            ) {
                Text(
                    "LEVEL UP!",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFFC107),
                    fontSize = 40.sp,
                    modifier = Modifier.background(WhitePure.copy(0.85f), RoundedCornerShape(12.dp)).padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun MissionItem(judul: String, expReward: Int, initiallyClaimed: Boolean, onClaim: () -> Unit) {
    var claimed by remember(initiallyClaimed) { mutableStateOf(initiallyClaimed) }

    Card(
        modifier = Modifier.fillMaxWidth().animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = if (claimed) Color.LightGray.copy(0.4f) else WhitePure),
        elevation = CardDefaults.cardElevation(defaultElevation = if (claimed) 0.dp else 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(judul, style = MaterialTheme.typography.titleMedium, color = if (claimed) Color.Gray else TextDark, fontWeight = if (claimed) FontWeight.Normal else FontWeight.Bold)
                Text("+$expReward EXP", color = if (claimed) Color.Gray else CoralPrimary, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            }
            Button(onClick = { onClaim(); claimed = true }, enabled = !claimed, shape = RoundedCornerShape(10.dp)) {
                Text(if (claimed) "Selesai" else "Klaim")
            }
        }
    }
}