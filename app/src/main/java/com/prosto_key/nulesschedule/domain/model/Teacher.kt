package com.prosto_key.nulesschedule.domain.model

data class Teacher(
    val teacherID: Int,
    val fullName: String,
    val phoneNumber: String?,
    val email: String?,
    val additionalInfo: String?
)
