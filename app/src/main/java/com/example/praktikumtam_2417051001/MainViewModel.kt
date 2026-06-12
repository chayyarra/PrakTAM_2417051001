package com.example.praktikumtam_2417051001

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var userEmail = mutableStateOf("")
    var exp = mutableIntStateOf(0)
    var level = mutableIntStateOf(1)
    val claimedMissions = mutableStateListOf<String>()

    fun addExp(amount: Int) {
        exp.intValue += amount
        if (exp.intValue >= level.intValue * 100) {
            level.intValue += 1
        }
    }

    fun resetSession() {
        userEmail.value = ""
        exp.intValue = 0
        level.intValue = 1
        claimedMissions.clear()
    }
}