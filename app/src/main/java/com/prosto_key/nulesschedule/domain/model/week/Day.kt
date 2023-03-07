package com.prosto_key.nulesschedule.domain.model.week

import com.prosto_key.nulesschedule.domain.model.Lesson

data class Day(
    val numeratorLessons: MutableList<Lesson?>,
    val denominatorLessons: MutableList<Lesson?>
)