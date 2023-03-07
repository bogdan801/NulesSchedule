package com.prosto_key.nulesschedule.domain.repository

import com.prosto_key.nulesschedule.domain.model.Schedule
import com.prosto_key.nulesschedule.domain.model.Subject
import com.prosto_key.nulesschedule.domain.model.Teacher
import com.prosto_key.nulesschedule.domain.model.time_schedule.LessonTime
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import kotlinx.coroutines.flow.Flow

interface Repository {
    //insert/edit
    suspend fun insertSchedule(schedule: Schedule)
    suspend fun editTimeScheduleLesson(lessonNumber: Int, newTime: LessonTime)
    suspend fun insertTeacherAndAddToASubject(teacher: Teacher, subjectID: Int, isLector: Boolean)
    suspend fun addToTeacherToASubject(teacherID: Int, subjectID: Int, isLector: Boolean)

    //delete
    suspend fun deleteSchedule(scheduleID: Int)
    suspend fun deleteTeacher(teacherID: Int)

    //select
    suspend fun getFullSchedule(scheduleID: Int): Schedule
    fun getScheduleFlow(): Flow<List<Schedule>>
    fun getTimeScheduleFlow(): Flow<TimeSchedule>

    //read excel
    suspend fun readScheduleFromExcel(): Schedule
}