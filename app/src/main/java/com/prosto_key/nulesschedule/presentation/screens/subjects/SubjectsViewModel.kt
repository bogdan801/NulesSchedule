package com.prosto_key.nulesschedule.presentation.screens.subjects

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prosto_key.nulesschedule.data.datastore.readIntFromDataStore
import com.prosto_key.nulesschedule.domain.model.Subject
import com.prosto_key.nulesschedule.domain.model.Teacher
import com.prosto_key.nulesschedule.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SubjectsViewModel
@Inject
constructor(
    private val repository: Repository,
    handle: SavedStateHandle,
    application: Application
): AndroidViewModel(application) {
    //CONTEXT
    private val context get() = getApplication<Application>()

    //SUBJECT LIST
    private val _scheduleID = mutableStateOf(-1)
    val scheduleID: State<Int> = _scheduleID

    private val _preOpenedSubjectID = mutableStateOf(-1)
    val preOpenedSubjectID: State<Int> = _preOpenedSubjectID

    private val _subjects = mutableStateOf(listOf<Subject>())
    val subjects: State<List<Subject>> = _subjects

    val preOpenedIndex by derivedStateOf {
        if(_preOpenedSubjectID.value != -1) {
            val element = subjects.value.find { subject ->
                subject.subjectID == _preOpenedSubjectID.value
            }
            if(element != null) subjects.value.indexOf(element) else 0
        }
        else 0
    }

    //ADD TEACHER SHEET
    private val _allTeachers = mutableStateOf(listOf<Teacher>())
    val allTeachers: State<List<Teacher>> = _allTeachers

    private val _currentSubject = mutableStateOf(Subject(0, "", null))

    fun setCurrentSubject(subject: Subject){
        _currentSubject.value = subject
    }

    fun addTeacher(teacher: Teacher, isNew: Boolean){
        viewModelScope.launch {
            if(isNew) {
                repository.insertTeacherAndAddToASubject(teacher, _currentSubject.value.subjectID, teacher.isLector)
            }
            else {
                if(_currentSubject.value.teachers != null && !_currentSubject.value.teachers!!.contains(teacher)){
                    repository.addToTeacherToASubject(teacher.teacherID, _currentSubject.value.subjectID, teacher.isLector)
                }
            }
        }
    }

    //DELETE TEACHER
    fun deleteTeacherFromSubject(teacher: Teacher){
        viewModelScope.launch {
            repository.deleteTeacher(teacher)
        }
    }

    init {
        _scheduleID.value = handle.get<Int>("scheduleID") ?: -1
        _preOpenedSubjectID.value = handle.get<Int>("referredSubjectID") ?: -1

        if(_scheduleID.value == -1){
            runBlocking {
                _scheduleID.value = context.readIntFromDataStore("openedScheduleID") ?: -1
            }
        }

        if(_scheduleID.value != -1){
            viewModelScope.launch {
                repository.getSubjectsWithTeachersFlow(_scheduleID.value).collect{ listOfSubjects ->
                    _subjects.value = listOfSubjects
                }
            }
        }

        viewModelScope.launch {
            repository.getTeachersFlow().collect{ teachers ->
                _allTeachers.value = teachers
            }
        }
    }
}