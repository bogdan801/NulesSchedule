package com.prosto_key.nulesschedule.domain.model.week

import com.prosto_key.nulesschedule.domain.model.Lesson

data class Day(
    val numeratorLessons: List<Lesson?>,
    val denominatorLessons: List<Lesson?>
)