package com.prosto_key.nulesschedule.data.local.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TeacherEntity::class,
            parentColumns = ["teacherID"],
            childColumns = ["teacherID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["subjectID"],
            childColumns = ["subjectID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TeachersOfSubjectEntity(
    @PrimaryKey(autoGenerate = true)
    val teachersOfSubjectID: Int,
    val teacherID: Int,
    val subjectID: Int,
    val isLector: Boolean
)
