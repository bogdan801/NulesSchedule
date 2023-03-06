package com.prosto_key.nulesschedule.data.mapper

import com.prosto_key.nulesschedule.data.local.database.entities.SubjectEntity
import com.prosto_key.nulesschedule.data.local.database.entities.TeacherEntity
import com.prosto_key.nulesschedule.data.local.database.entities.TimeScheduleEntity
import com.prosto_key.nulesschedule.domain.model.Subject
import com.prosto_key.nulesschedule.domain.model.Teacher
import com.prosto_key.nulesschedule.domain.model.time_schedule.LessonTime
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule

//time schedule
fun LessonTime.toTimeScheduleEntity(lessonNumberIndex: Int) = TimeScheduleEntity(lessonNumberIndex, start, end)
fun TimeScheduleEntity.toLessonTime() = LessonTime(start = startTime, end = endTime)

fun List<TimeScheduleEntity>.toTimeSchedule() = TimeSchedule(this.map { it.toLessonTime() })

//teacher
fun Teacher.toTeacherEntity() = TeacherEntity(
    teacherID = teacherID,
    fullName = fullName,
    phoneNumber = phoneNumber,
    email = email,
    additionalInfo = additionalInfo
)

fun TeacherEntity.toTeacher() = Teacher(
    teacherID = teacherID,
    fullName = fullName,
    phoneNumber = phoneNumber,
    email = email,
    additionalInfo = additionalInfo
)

//subject
fun Subject.toSubjectEntity(scheduleID: Int) = SubjectEntity(
    subjectID = subjectID,
    scheduleID = scheduleID,
    name = name
)

fun SubjectEntity.toSubject(teachers: List<Teacher>? = null) = Subject(
    subjectID = subjectID,
    name = name,
    teachers = teachers
)
