package com.example.taimin.clases
import android.provider.Settings.Global.getString
import com.example.taimin.R

enum class Repeticion {
    DAILY, WEEKLY, MONTHLY, YEARLY;

    fun resource(): Int {
        return when (this){
            DAILY -> R.string.DAILY
            WEEKLY -> R.string.WEEKLY
            MONTHLY -> R.string.MONTHLY
            YEARLY -> R.string.YEARLY
        }
    }
}
