package com.prosto_key.nulesschedule.data.repository

import com.prosto_key.nulesschedule.data.local.database.Dao
import com.prosto_key.nulesschedule.domain.model.Schedule
import com.prosto_key.nulesschedule.domain.model.Teacher
import com.prosto_key.nulesschedule.domain.model.time_schedule.LessonTime
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val dao: Dao):Repository {
    //insert/edit
    override suspend fun insertSchedule(schedule: Schedule) {
        TODO("Not yet implemented")
    }

    override suspend fun editTimeScheduleLesson(lessonNumber: Int, newTime: LessonTime) {
        TODO("Not yet implemented")
    }

    override suspend fun insertTeacherAndAddToASubject(teacher: Teacher) {
        TODO("Not yet implemented")
    }

    override suspend fun addToTeacherToASubject(teacherID: Int) {
        TODO("Not yet implemented")
    }

    //delete
    override suspend fun deleteSchedule(scheduleID: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTeacher(teacherID: Int) {
        TODO("Not yet implemented")
    }

    //select
    override suspend fun getFullSchedule(scheduleID: Int): Schedule {
        TODO("Not yet implemented")
    }

    override fun getScheduleFlow(): Flow<Schedule> {
        TODO("Not yet implemented")
    }

    override fun getTimeScheduleFlow(): Flow<TimeSchedule> {
        TODO("Not yet implemented")
    }

    //read excel
    override suspend fun readScheduleFromExcel(): Schedule {
        TODO("Not yet implemented")
    }
}