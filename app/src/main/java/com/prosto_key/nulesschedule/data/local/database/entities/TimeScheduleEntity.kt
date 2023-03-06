package com.prosto_key.nulesschedule.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimeScheduleEntity(
    @PrimaryKey(autoGenerate = false)
    val lessonNumber: Int,
    val startTime: String,
    val endTime: String
)
