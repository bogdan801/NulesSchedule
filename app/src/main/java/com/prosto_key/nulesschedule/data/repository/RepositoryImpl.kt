package com.prosto_key.nulesschedule.data.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.prosto_key.nulesschedule.data.local.database.Dao
import com.prosto_key.nulesschedule.data.local.database.entities.LessonEntity
import com.prosto_key.nulesschedule.data.local.database.entities.SubjectEntity
import com.prosto_key.nulesschedule.data.local.database.entities.TeachersOfSubjectEntity
import com.prosto_key.nulesschedule.data.local.database.entities.TimeScheduleEntity
import com.prosto_key.nulesschedule.data.local.excel_parsing.*
import com.prosto_key.nulesschedule.data.mapper.*
import com.prosto_key.nulesschedule.data.util.cutSubjectNameFromLesson
import com.prosto_key.nulesschedule.data.util.isSimilarTo
import com.prosto_key.nulesschedule.domain.model.Lesson
import com.prosto_key.nulesschedule.domain.model.Schedule
import com.prosto_key.nulesschedule.domain.model.Teacher
import com.prosto_key.nulesschedule.domain.model.time_schedule.LessonTime
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.domain.model.week.Week
import com.prosto_key.nulesschedule.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class RepositoryImpl(private val dao: Dao):Repository {
    //insert/edit
    override suspend fun insertSchedule(schedule: Schedule): Int {
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

        return scheduleID
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

    override suspend fun isScheduleUnique(schedule: Schedule): Boolean {
        val dbSchedules = dao.getSchedulesEntities().first().map { it.toSchedule(null) }
        return !dbSchedules.contains(schedule)
    }

    override suspend fun getScheduleID(schedule: Schedule): Int {
        val found = dao.getScheduleByItsContents(schedule.fileName, schedule.major, schedule.year, schedule.group).toSchedule(null)
        return found.scheduleID
    }

    //read excel
    private val fileBuffer: MutableState<ScheduleFileBuffer> = mutableStateOf(ScheduleFileBuffer())

    override fun readMajorsFromFile(workbook: XSSFWorkbook, sheet: Int)
        = getMajorsFromWorkBook(workbook, sheet, fileBuffer)
    override fun readYearsOfMajor(majorList: List<String>, majorID: Int)
        = getYearsFromMajor(fileBuffer.value, majorList, majorID)
    override fun readGroupsFromMajorAndYear(majorList: List<String>, majorID: Int, yearList: List<String>, yearID: Int)
        = getGroupsFromMajorAndYear(fileBuffer.value, majorList, majorID, yearList, yearID)
    override fun readWeekFromFile(workbook: XSSFWorkbook, sheet: Int, major: String, year: String, group: String) =
        getWeek(workbook, sheet, major, year, group)
}