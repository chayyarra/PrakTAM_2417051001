package com.example.praktikumtam_2417051001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.example.praktikumtam_2417051001.model.ActivityModel
import com.example.praktikumtam_2417051001.network.RetrofitClient
import com.example.praktikumtam_2417051001.ui.theme.PraktikumTAM_2417051001Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val BgTop = Color(0xFFFFFFFF)
val BgMid = Color(0xFFF0F8FF)
val BgBottom = Color(0xFFE1F5FE)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraktikumTAM_2417051001Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var selectedActivity by remember { mutableStateOf<ActivityModel?>(null) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            DaftarActivityScreen(onNavigateToDetail = { data ->
                selectedActivity = data
                navController.navigate("detail")
            })
        }
        composable("detail") {
            selectedActivity?.let { data ->
                DetailScreen(activityData = data, onNavigateBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun DaftarActivityScreen(onNavigateToDetail: (ActivityModel) -> Unit) {
    var activities by remember { mutableStateOf<List<ActivityModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            activities = RetrofitClient.instance.getActivities()
            isError = false
        } catch (e: Exception) {
            isError = true
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (isError) {
        ErrorView {
            isLoading = true
            isError = false
            // Re-fetch logic here
        }
    } else {
        Scaffold { padding ->
            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(BgTop, BgMid, BgBottom))).padding(padding)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
                    verticalArrangement = Arrangement.spacedBy(22.dp)
                ) {
                    item {
                        Text("Aktivitas Populer", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(14.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                            items(activities.take(5)) { data ->
                                ActivityRowItem(data) { onNavigateToDetail(data) }
                            }
                        }
                        Spacer(modifier = Modifier.height(28.dp))
                        Text("Semua Aktivitas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                    items(activities) { data ->
                        ActivityCard(data) { onNavigateToDetail(data) }
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityCard(activityData: ActivityModel, onClick: () -> Unit) {
    var isFavorite by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(100.dp)) {
                // Menggunakan SubcomposeAsyncImage agar bisa menghapus painterResource
                SubcomposeAsyncImage(
                    model = activityData.imageRes,
                    contentDescription = null,
                    loading = { Box(Modifier.fillMaxSize().background(Color.LightGray)) },
                    error = { Icon(Icons.Default.Place, contentDescription = null, tint = Color.Gray) },
                    modifier = Modifier.matchParentSize().clip(RoundedCornerShape(22.dp)),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier.align(Alignment.TopEnd).padding(6.dp).size(30.dp).background(Color.White.copy(0.9f), RoundedCornerShape(50))
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorite) Color.Red else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(activityData.nama, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(activityData.deskripsi, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onClick, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp)) {
                    Text("Detail")
                }
            }
        }
    }
}

@Composable
fun ActivityRowItem(activityData: ActivityModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier.width(170.dp).clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            SubcomposeAsyncImage(
                model = activityData.imageRes,
                contentDescription = null,
                loading = { Box(Modifier.fillMaxWidth().height(115.dp).background(Color.LightGray)) },
                error = { Box(Modifier.fillMaxWidth().height(115.dp).background(Color.Gray)) },
                modifier = Modifier.fillMaxWidth().height(115.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(activityData.nama, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(activityData.deskripsi, style = MaterialTheme.typography.bodySmall, color = Color.Gray, maxLines = 1)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(activityData: ActivityModel, onNavigateBack: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    var isProcessing by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Aktivitas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            SubcomposeAsyncImage(
                model = activityData.imageRes,
                contentDescription = null,
                loading = { CircularProgressIndicator() },
                modifier = Modifier.size(240.dp).clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(activityData.nama, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Target: ${activityData.deskripsi}", color = Color.Gray)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    coroutineScope.launch {
                        isProcessing = true
                        delay(1500)
                        snackbarHostState.showSnackbar("Selesai!")
                        isProcessing = false
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = !isProcessing
            ) {
                if (isProcessing) CircularProgressIndicator(modifier = Modifier.size(24.dp)) else Text("Selesaikan")
            }
        }
    }
}

@Composable
fun ErrorView(onRetry: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(32.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Gagal Memuat Data", color = Color.Red, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) { Text("Coba Lagi") }
    }
}