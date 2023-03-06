package com.prosto_key.nulesschedule.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeacherEntity(
    @PrimaryKey(autoGenerate = true)
    val teacherID: Int,
    val fullName: String,
    val phoneNumber: String?,
    val email: String?,
    val additionalInfo: String?
)
