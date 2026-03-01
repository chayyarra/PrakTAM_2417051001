package com.example.praktikumtam_2417051001.model

import androidx.annotation.DrawableRes

data class Activity(
    val nama: String,
    val deskripsi: String,
    @DrawableRes val imageRes: Int
)