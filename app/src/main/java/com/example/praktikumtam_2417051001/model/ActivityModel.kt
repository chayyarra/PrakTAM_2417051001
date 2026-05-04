package com.example.praktikumtam_2417051001.model

import com.google.gson.annotations.SerializedName

data class ActivityModel(
    @SerializedName("nama")
    val nama: String,

    @SerializedName("deskripsi")
    val deskripsi: String,

    @SerializedName("imageRes")
    val imageRes: String
)