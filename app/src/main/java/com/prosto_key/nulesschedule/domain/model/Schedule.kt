package com.prosto_key.nulesschedule.domain.model

import com.prosto_key.nulesschedule.domain.model.week.Week

data class Schedule(
    val scheduleID: Int = 0,
    val fileName: String = "",
    val major: String = "",
    val year: String = "",
    val group: String = "",
    val week: Week? = null
)
