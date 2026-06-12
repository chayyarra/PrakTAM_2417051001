package com.example.praktikumtam_2417051001.uii.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.praktikumtam_2417051001.model.ActivityModel
import com.example.praktikumtam_2417051001.data.api.RetrofitClient
import com.example.praktikumtam_2417051001.ui.theme.CoralPrimary
import com.example.praktikumtam_2417051001.ui.theme.TextDark
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetail: (ActivityModel) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToMissions: () -> Unit
) {
    var activities by remember { mutableStateOf<List<ActivityModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    val greetingText = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when (hour) {
            in 4..11 -> "Selamat Pagi ✨"
            in 12..14 -> "Selamat Siang ☀️"
            in 15..18 -> "Selamat Sore 🌅"
            else -> "Selamat Malam 🌙"
        }
    }

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

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(greetingText, fontWeight = FontWeight.ExtraBold, color = CoralPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    IconButton(onClick = onNavigateToMissions) {
                        Icon(Icons.Default.Star, contentDescription = "Misi Harian", tint = Color(0xFFFFC107))
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profil", tint = TextDark)
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(padding)) {
            if (isLoading) {
                HomeScreenShimmer()
            } else if (isError) {
                ErrorView {
                    isLoading = true
                    isError = false
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item {
                        Text("Paling Populer", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextDark)
                        Spacer(modifier = Modifier.height(14.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(activities.take(5)) { data ->
                                ActivityRowItem(data) { onNavigateToDetail(data) }
                            }
                        }
                        Spacer(modifier = Modifier.height(28.dp))
                        Text("Semua Aktivitas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextDark)
                        Spacer(modifier = Modifier.height(8.dp))
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
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            SubcomposeAsyncImage(
                model = activityData.imageRes,
                contentDescription = null,
                loading = { ShimmerBox(Modifier.fillMaxSize()) },
                modifier = Modifier.size(90.dp).clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(activityData.nama, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextDark)
                Text(activityData.deskripsi, style = MaterialTheme.typography.bodyMedium, color = Color.Gray, maxLines = 2)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onClick,
                    modifier = Modifier.align(Alignment.End).height(36.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Detail")
                }
            }
        }
    }
}

@Composable
fun ActivityRowItem(activityData: ActivityModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier.width(180.dp).clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            SubcomposeAsyncImage(
                model = activityData.imageRes,
                contentDescription = null,
                loading = { ShimmerBox(Modifier.fillMaxSize()) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(activityData.nama, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextDark, maxLines = 1)
            }
        }
    }
}

@Composable
fun ShimmerBox(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(animation = tween(1000, easing = LinearEasing)),
        label = "shimmer"
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim, y = translateAnim)
    )
    Box(modifier = modifier.background(brush))
}

@Composable
fun HomeScreenShimmer() {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        ShimmerBox(Modifier.width(150.dp).height(24.dp))
        Spacer(modifier = Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            repeat(2) { ShimmerBox(Modifier.width(180.dp).height(180.dp).clip(RoundedCornerShape(20.dp))) }
        }
        Spacer(modifier = Modifier.height(28.dp))
        ShimmerBox(Modifier.width(200.dp).height(24.dp))
        Spacer(modifier = Modifier.height(16.dp))
        repeat(2) {
            ShimmerBox(Modifier.fillMaxWidth().height(114.dp).clip(RoundedCornerShape(20.dp)))
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ErrorView(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Gagal Memuat Data", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRetry) { Text("Coba Lagi") }
    }
}