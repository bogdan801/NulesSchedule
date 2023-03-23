package com.prosto_key.nulesschedule.presentation.screens.archive

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prosto_key.nulesschedule.data.datastore.readIntFromDataStore
import com.prosto_key.nulesschedule.data.datastore.saveIntToDataStore
import com.prosto_key.nulesschedule.domain.model.Schedule
import com.prosto_key.nulesschedule.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject
constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {
    //CONTEXT
    private val context
        get() = getApplication<Application>()

    //ALL SCHEDULES
    private val _schedulesList = mutableStateOf<List<Schedule>>(listOf())
    val schedulesList: State<List<Schedule>> = _schedulesList

    private val _selectedSchedule = mutableStateOf(-1)
    val selectedSchedule: State<Int> = _selectedSchedule

    suspend fun selectSchedule(scheduleID: Int){
        _selectedSchedule.value = scheduleID
        context.saveIntToDataStore("openedScheduleID", scheduleID)
    }

    //DELETE SCHEDULE
    fun deleteSchedule(schedule: Schedule, id: Int){
        viewModelScope.launch {
            if(schedule.scheduleID == selectedSchedule.value) {
                selectSchedule(if (_schedulesList.value.size != 1) _schedulesList.value[if(id == 0) 1 else 0].scheduleID else -1)
            }
            repository.deleteSchedule(schedule.scheduleID)
            repository.deleteRedundantTeachers()
        }
    }

    init {
        viewModelScope.launch {
            _selectedSchedule.value = context.readIntFromDataStore("openedScheduleID") ?: -1
        }
        viewModelScope.launch {
            repository.getScheduleFlow().collect{ listOfSchedules ->
                _schedulesList.value = listOfSchedules
            }
        }
    }
}