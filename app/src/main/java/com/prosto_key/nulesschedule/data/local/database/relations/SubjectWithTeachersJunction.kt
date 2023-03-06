package com.prosto_key.nulesschedule.data.local.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.prosto_key.nulesschedule.data.local.database.entities.SubjectEntity
import com.prosto_key.nulesschedule.data.local.database.entities.TeacherEntity
import com.prosto_key.nulesschedule.data.local.database.entities.TeachersOfSubjectEntity

data class SubjectWithTeachersJunction(
    @Embedded
    val subject: SubjectEntity,
    @Relation(
        parentColumn = "subjectID",
        entityColumn = "subjectID"
    )
    val teachers: List<TeachersOfSubjectEntity>
)
