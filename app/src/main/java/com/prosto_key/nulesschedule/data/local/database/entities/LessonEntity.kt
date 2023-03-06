package com.prosto_key.nulesschedule.data.local.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["subjectID"],
            childColumns = ["subjectID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ScheduleEntity::class,
            parentColumns = ["scheduleID"],
            childColumns = ["scheduleID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LessonEntity(
    @PrimaryKey(autoGenerate = true)
    val lessonID: Int,
    val subjectID: Int,
    val scheduleID: Int,
    val name: String,
    val number: Int,
    val day: Int,
    val isNumerator: Boolean
)
