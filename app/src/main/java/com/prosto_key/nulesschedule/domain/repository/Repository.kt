package com.prosto_key.nulesschedule.domain.repository

import androidx.compose.runtime.MutableState
import com.prosto_key.nulesschedule.data.local.excel_parsing.ScheduleFileBuffer
import com.prosto_key.nulesschedule.domain.model.Schedule
import com.prosto_key.nulesschedule.domain.model.Subject
import com.prosto_key.nulesschedule.domain.model.Teacher
import com.prosto_key.nulesschedule.domain.model.time_schedule.LessonTime
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.domain.model.week.Week
import kotlinx.coroutines.flow.Flow
import org.apache.poi.xssf.usermodel.XSSFWorkbook

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
    fun readMajorsFromFile(workbook: XSSFWorkbook, sheet: Int): List<String>
    fun readYearsOfMajor(majorList: List<String>, majorID: Int): List<String>
    fun readGroupsFromMajorAndYear(majorList: List<String>, majorID: Int, yearList: List<String>, yearID: Int): List<String>
    fun readWeekFromFile(workbook: XSSFWorkbook, sheet: Int, major: String, year: String, group: String): Week?
}