package com.example.praktikumtam_2417051001.data.api

import com.example.praktikumtam_2417051001.model.ActivityModel
import retrofit2.http.GET

interface ApiService {
    @GET("menu_activity.json")
    suspend fun getActivities(): List<ActivityModel>
}
