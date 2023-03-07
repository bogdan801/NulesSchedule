package com.prosto_key.nulesschedule.data.repository

import com.prosto_key.nulesschedule.data.local.database.Dao
import com.prosto_key.nulesschedule.data.local.database.entities.LessonEntity
import com.prosto_key.nulesschedule.data.local.database.entities.SubjectEntity
import com.prosto_key.nulesschedule.data.local.database.entities.TeachersOfSubjectEntity
import com.prosto_key.nulesschedule.data.local.database.entities.TimeScheduleEntity
import com.prosto_key.nulesschedule.data.mapper.*
import com.prosto_key.nulesschedule.data.util.cutSubjectNameFromLesson
import com.prosto_key.nulesschedule.data.util.isSimilarTo
import com.prosto_key.nulesschedule.domain.model.Lesson
import com.prosto_key.nulesschedule.domain.model.Schedule
import com.prosto_key.nulesschedule.domain.model.Teacher
import com.prosto_key.nulesschedule.domain.model.time_schedule.LessonTime
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(private val dao: Dao):Repository {
    //insert/edit
    override suspend fun insertSchedule(schedule: Schedule) {
        val scheduleID = dao.insertScheduleEntity(schedule.toScheduleEntity()).toInt()

        if(schedule.week != null){
            val lessonList = mutableListOf<LessonEntity>()
            schedule.week.days.forEachIndexed { dayIndex, day ->
                day.numeratorLessons.forEachIndexed { lessonIndex, numLesson ->
                    if(numLesson!=null) lessonList.add(numLesson.toLessonEntity(scheduleID, dayIndex, lessonIndex, true))
                }
                day.denominatorLessons.forEachIndexed { lessonIndex, denLesson ->
                    if(denLesson!=null) lessonList.add(denLesson.toLessonEntity(scheduleID, dayIndex, lessonIndex, false))
                }
            }

            val subjectList = mutableListOf<SubjectEntity>()
            for(i in 0..lessonList.lastIndex){
                var subjectIsInTheList = false
                for(subject in subjectList){
                    if(subject.name.isSimilarTo(cutSubjectNameFromLesson(lessonList[i].name), 0.7)) {
                        dao.insertLessonEntity(lessonList[i].copy(subjectID = subject.subjectID))
                        subjectIsInTheList = true
                    }
                }
                if(!subjectIsInTheList){
                    val subjectEntity = SubjectEntity(0, scheduleID, cutSubjectNameFromLesson(lessonList[i].name))
                    val subjectID = dao.insertSubjectEntity(subjectEntity).toInt()
                    subjectList.add(subjectEntity.copy(subjectID = subjectID))
                    dao.insertLessonEntity(lessonList[i].copy(subjectID = subjectID))
                }
            }
        }
    }

    override suspend fun editTimeScheduleLesson(lessonNumber: Int, newTime: LessonTime) {
        dao.insertTimeScheduleEntity(newTime.toTimeScheduleEntity(lessonNumber))
    }

    override suspend fun insertTeacherAndAddToASubject(teacher: Teacher, subjectID: Int, isLector: Boolean) {
        val teacherID = dao.insertTeacherEntity(teacher.toTeacherEntity()).toInt()
        dao.insertTeachersOfSubjectEntity(TeachersOfSubjectEntity(teachersOfSubjectID = 0, teacherID = teacherID, subjectID = subjectID, isLector = isLector))
    }

    override suspend fun addToTeacherToASubject(teacherID: Int, subjectID: Int, isLector: Boolean) {
        dao.insertTeachersOfSubjectEntity(TeachersOfSubjectEntity(teachersOfSubjectID = 0, teacherID = teacherID, subjectID = subjectID, isLector = isLector))
    }

    //delete
    override suspend fun deleteSchedule(scheduleID: Int) {
        dao.deleteScheduleEntity(scheduleID)
    }

    override suspend fun deleteTeacher(teacherID: Int) {
        dao.deleteTeacherEntity(teacherID)
    }

    //select
    override suspend fun getFullSchedule(scheduleID: Int) = dao.getScheduleWithLessons(scheduleID).toSchedule()

    override fun getScheduleFlow(): Flow<List<Schedule>> = dao.getSchedulesEntities().map { list ->
        list.map { scheduleEntity ->
            scheduleEntity.toSchedule(null)
        }
    }

    override fun getTimeScheduleFlow(): Flow<TimeSchedule> = dao.getTimeScheduleEntities().map { entities ->
        entities.toTimeSchedule()
    }

    //read excel
    override suspend fun readScheduleFromExcel(): Schedule {
        TODO("Not yet implemented")
    }
}