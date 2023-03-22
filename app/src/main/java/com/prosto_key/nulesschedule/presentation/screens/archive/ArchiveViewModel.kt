package com.prosto_key.nulesschedule.presentation.screens.archive

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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

    fun selectSchedule(scheduleID: Int){
        viewModelScope.launch {
            context.saveIntToDataStore("openedScheduleID", scheduleID)
        }
    }

    init {
        viewModelScope.launch {
            repository.getScheduleFlow().collect{ listOfSchedules ->
                _schedulesList.value = listOfSchedules
            }
        }
    }
}