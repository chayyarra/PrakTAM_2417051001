package com.example.praktikumtam_2417051001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.praktikumtam_2417051001.model.Activity
import com.example.praktikumtam_2417051001.model.ActivityDay
import com.example.praktikumtam_2417051001.ui.theme.PraktikumTAM_2417051001Theme

val BgTop = Color(0xFFFFF1F5)
val BgMid = Color(0xFFEDE7FF)
val BgBottom = Color(0xFFE0F7FA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PraktikumTAM_2417051001Theme {
                Scaffold { padding ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(BgTop, BgMid, BgBottom)
                                )
                            )
                            .padding(padding)
                    ) {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                horizontal = 20.dp,
                                vertical = 18.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(22.dp)
                        ) {

                            item {
                                Text(
                                    text = "Aktivitas Populer",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    items(ActivityDay.dummyActivity) { activity ->
                                        ActivityRowItem(activity)
                                    }
                                }

                                Spacer(modifier = Modifier.height(28.dp))

                                Text(
                                    text = "Semua Aktivitas",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                                Spacer(modifier = Modifier.height(6.dp))
                            }

                            items(ActivityDay.dummyActivity) { activity ->
                                ActivityCard(activity)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityCard(activity: Activity) {

    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {

        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier.size(100.dp)
                ) {

                    Image(
                        painter = painterResource(activity.imageRes),
                        contentDescription = activity.nama,
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RoundedCornerShape(22.dp)),
                        contentScale = ContentScale.Crop
                    )

                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .size(30.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.9f),
                                shape = RoundedCornerShape(50)
                            )
                    ) {
                        Icon(
                            imageVector = if (isFavorite)
                                Icons.Filled.Favorite
                            else
                                Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite)
                                MaterialTheme.colorScheme.primary
                            else Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = activity.nama,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = activity.deskripsi,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            "Lihat Detail",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityRowItem(activity: Activity) {

    Card(
        modifier = Modifier.width(170.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            Image(
                painter = painterResource(activity.imageRes),
                contentDescription = activity.nama,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {

                Text(
                    text = activity.nama,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = activity.deskripsi,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 2
                )
            }
        }
    }
}