package com.prosto_key.nulesschedule.data.mapper

import com.prosto_key.nulesschedule.data.local.database.entities.*
import com.prosto_key.nulesschedule.domain.model.Lesson
import com.prosto_key.nulesschedule.domain.model.Schedule
import com.prosto_key.nulesschedule.domain.model.Subject
import com.prosto_key.nulesschedule.domain.model.Teacher
import com.prosto_key.nulesschedule.domain.model.time_schedule.LessonTime
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.domain.model.week.Week

//time schedule
fun LessonTime.toTimeScheduleEntity(lessonNumberIndex: Int) = TimeScheduleEntity(lessonNumberIndex, start, end)
fun TimeScheduleEntity.toLessonTime() = LessonTime(start = startTime, end = endTime)

fun List<TimeScheduleEntity>.toTimeSchedule() = TimeSchedule(this.map { it.toLessonTime() })

//schedule
fun Schedule.toScheduleEntity() = ScheduleEntity(
    scheduleID = scheduleID,
    fileName = fileName,
    major = major,
    year = year,
    group = group
)

fun ScheduleEntity.toSchedule(week: Week?) = Schedule(
    scheduleID = scheduleID,
    fileName = fileName,
    major = major,
    year = year,
    group = group,
    week = week
)

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

//lesson
fun Lesson.toLessonEntity(scheduleID: Int, day: Int, number: Int, isNumerator: Boolean) = LessonEntity(
    lessonID = lessonID,
    subjectID = subjectID,
    scheduleID = scheduleID,
    name = lessonName,
    day = day,
    number = number,
    isNumerator = isNumerator
)

fun LessonEntity.toLesson() = Lesson(
    lessonID = lessonID,
    subjectID = subjectID,
    lessonName = name
)

