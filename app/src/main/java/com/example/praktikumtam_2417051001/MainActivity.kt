package com.example.praktikumtam_2417051001

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.praktikumtam_2417051001.navigation.AppNavigation
import com.example.praktikumtam_2417051001.ui.theme.PraktikumTAM_2417051001Theme

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