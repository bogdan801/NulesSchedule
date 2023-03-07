package com.prosto_key.nulesschedule.data.mapper

import com.prosto_key.nulesschedule.data.local.database.entities.*
import com.prosto_key.nulesschedule.data.local.database.relations.ScheduleWithLessonsJunction
import com.prosto_key.nulesschedule.domain.model.Lesson
import com.prosto_key.nulesschedule.domain.model.Schedule
import com.prosto_key.nulesschedule.domain.model.Subject
import com.prosto_key.nulesschedule.domain.model.Teacher
import com.prosto_key.nulesschedule.domain.model.time_schedule.LessonTime
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.domain.model.week.Day
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

fun List<LessonEntity>.toWeek(): Week{
    val days = mutableListOf<Day>()
    for(dayID in 0..4){
        val numLessons = mutableListOf<Lesson?>()
        val denLessons = mutableListOf<Lesson?>()
        for (lessonNumber in 0..6){
            numLessons.add(null)
            denLessons.add(null)
        }
        days.add(Day(numLessons, denLessons))
    }

    this.forEach{ lessonEntity ->
        if(lessonEntity.isNumerator) days[lessonEntity.day].numeratorLessons[lessonEntity.number] = lessonEntity.toLesson()
        else days[lessonEntity.day].denominatorLessons[lessonEntity.number] = lessonEntity.toLesson()
    }

    return Week(days = days)
}

fun ScheduleWithLessonsJunction.toSchedule() = Schedule(
    scheduleID = scheduleEntity.scheduleID,
    fileName = scheduleEntity.fileName,
    major = scheduleEntity.major,
    year = scheduleEntity.year,
    group = scheduleEntity.group,
    week = lessons.toWeek()
)

