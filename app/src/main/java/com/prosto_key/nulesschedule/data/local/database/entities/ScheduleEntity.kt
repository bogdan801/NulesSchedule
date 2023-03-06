package com.prosto_key.nulesschedule.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val scheduleID: Int,
    val fileName: String,
    val major: String,
    val year: String,
    val group: String
)
