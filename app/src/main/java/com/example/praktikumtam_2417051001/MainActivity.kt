package com.example.praktikumtam_2417051001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktikumtam_2417051001.model.activityDay
import com.example.praktikumtam_2417051001.ui.theme.PraktikumTAM_2417051001Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraktikumTAM_2417051001Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        activityDay.dummyActivity.forEach { activity ->
            Text(text = "Nama: ${activity.nama}")
            Text(text = "Deskripsi: ${activity.deskripsi}")
            Text(text = "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PraktikumTAM_2417051001Theme {
        Greeting()
    }
}