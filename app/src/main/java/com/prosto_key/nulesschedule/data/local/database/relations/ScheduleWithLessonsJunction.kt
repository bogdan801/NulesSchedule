package com.prosto_key.nulesschedule.data.local.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.prosto_key.nulesschedule.data.local.database.entities.LessonEntity
import com.prosto_key.nulesschedule.data.local.database.entities.ScheduleEntity

data class ScheduleWithLessonsJunction(
    @Embedded
    val scheduleEntity: ScheduleEntity,
    @Relation(
        parentColumn = "scheduleID",
        entityColumn = "scheduleID"
    )
    val lessons: List<LessonEntity>
)
