package com.prosto_key.nulesschedule.data.local.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ScheduleEntity::class,
            parentColumns = ["scheduleID"],
            childColumns = ["scheduleID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true)
    val subjectID: Int,
    val scheduleID: Int,
    val name: String
)
