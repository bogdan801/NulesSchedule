package com.prosto_key.nulesschedule.domain.model

data class Subject(
    val subjectID: Int,
    val name: String,
    val teachers: List<Teacher>?
)
