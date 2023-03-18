package com.prosto_key.nulesschedule.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prosto_key.nulesschedule.data.local.database.entities.*
import com.prosto_key.nulesschedule.data.local.database.relations.ScheduleWithLessonsJunction
import com.prosto_key.nulesschedule.data.local.database.relations.SubjectWithTeachersJunction
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    //INSERT/UPDATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeScheduleEntity(timeScheduleEntity: TimeScheduleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScheduleEntity(scheduleEntity: ScheduleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeacherEntity(teacherEntity: TeacherEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjectEntity(subjectEntity: SubjectEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonEntity(lessonEntity: LessonEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeachersOfSubjectEntity(teachersOfSubjectEntity: TeachersOfSubjectEntity): Long

    //DELETE
    @Query("DELETE FROM scheduleentity WHERE scheduleID == :id")
    suspend fun deleteScheduleEntity(id: Int)

    @Query("DELETE FROM subjectentity WHERE subjectID == :id")
    suspend fun deleteSubjectEntity(id: Int)

    @Query("DELETE FROM teacherentity WHERE teacherID == :id")
    suspend fun deleteTeacherEntity(id: Int)

    @Query("DELETE FROM teachersofsubjectentity WHERE subjectID == :subjectID AND teacherID == :teacherID")
    suspend fun deleteTeachersOfSubjectEntity(subjectID: Int, teacherID: Int)

    @Query("DELETE FROM lessonentity WHERE lessonID == :id")
    suspend fun deleteLessonEntity(id: Int)

    //SELECT
    @Query("SELECT * FROM teachersofsubjectentity")
    suspend fun isTeacherInAnySubject(): List<TeachersOfSubjectEntity>

    @Query("SELECT * FROM timescheduleentity")
    fun getTimeScheduleEntities(): Flow<List<TimeScheduleEntity>>

    @Query("SELECT * FROM scheduleentity")
    fun getSchedulesEntities(): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM scheduleentity WHERE scheduleID == :id")
    suspend fun getScheduleWithLessons(id: Int): ScheduleWithLessonsJunction

    @Query("SELECT * FROM subjectentity WHERE scheduleID == :scheduleID ORDER BY name ASC")
    fun getSubjectEntities(scheduleID: Int): Flow<List<SubjectWithTeachersJunction>>

    @Query("SELECT * FROM teacherentity WHERE teacherID == :id")
    suspend fun getTeacherEntity(id: Int): TeacherEntity

    @Query("SELECT * FROM subjectentity WHERE subjectID == :id")
    suspend fun getSubjectWithTeachers(id: Int): SubjectWithTeachersJunction

    @Query("SELECT * FROM scheduleentity WHERE fileName == :fileName AND major == :major AND year == :year AND `group` == :group")
    suspend fun getScheduleByItsContents(fileName: String, major: String, year: String, group: String): ScheduleEntity
}