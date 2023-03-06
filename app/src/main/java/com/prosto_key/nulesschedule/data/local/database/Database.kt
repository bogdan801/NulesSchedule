package com.prosto_key.nulesschedule.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prosto_key.nulesschedule.data.local.database.entities.*

@Database(
    entities = [
        TimeScheduleEntity::class,
        ScheduleEntity::class,
        SubjectEntity::class,
        TeacherEntity::class,
        LessonEntity::class,
        TeachersOfSubjectEntity::class
    ],
    exportSchema = true,
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract val dbDao: Dao
}
