package com.example.praktikumtam_2417051001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
val CardWhite = Color(0xFFFFFFFF)
val TitleColor = Color(0xFF2D2D2D)
val SubColor = Color(0xFF7A7A7A)

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
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 18.dp, vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
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
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .size(95.dp)
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
                        .padding(8.dp)
                        .size(30.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.85f),
                            shape = RoundedCornerShape(50)
                        )
                ) {
                    Icon(
                        imageVector = if (isFavorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color(0xFFFF6B81) else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = activity.nama,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TitleColor
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = activity.deskripsi,
                    style = MaterialTheme.typography.bodyMedium,
                    color = SubColor
                )
            }
        }
    }
}