package com.example.praktikumtam_2417051001.data.repository

import com.example.praktikumtam_2417051001.data.api.RetrofitClient
import com.example.praktikumtam_2417051001.model.ActivityModel

class ActivityRepositor {
    suspend fun getActivities(): List<ActivityModel> {
        return try{
            RetrofitClient.instance.getActivities()
        } catch (e: Exception){
            emptyList()
        }
    }
}