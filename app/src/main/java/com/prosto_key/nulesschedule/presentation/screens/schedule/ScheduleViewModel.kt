package com.prosto_key.nulesschedule.presentation.screens.schedule

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prosto_key.nulesschedule.data.datastore.readIntFromDataStore
import com.prosto_key.nulesschedule.domain.model.Schedule
import com.prosto_key.nulesschedule.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ScheduleViewModel
@Inject
constructor(
    private val repository: Repository,
    handle: SavedStateHandle,
    application: Application,
    workBookState: MutableState<XSSFWorkbook>
): AndroidViewModel(application) {
    private val context
        get() = getApplication<Application>()

    private val _selectedScheduleID =  mutableStateOf(-1)
    val selectedScheduleID: State<Int> =  _selectedScheduleID

    private val schedule =  mutableStateOf(Schedule())

    val week by derivedStateOf {
        schedule.value.week
    }

    init {
        val currentScheduleId = handle.get<Int>("scheduleID") ?: -1

        if(currentScheduleId == -1){
            val result = viewModelScope.async {
                context.readIntFromDataStore("openedScheduleID")

            }
            result.invokeOnCompletion {
                _selectedScheduleID.value = result.getCompleted() ?: -1
            }
        }
        else{
            _selectedScheduleID.value = currentScheduleId
        }

        if(_selectedScheduleID.value != -1){
            viewModelScope.launch {
                schedule.value = repository.getFullSchedule(_selectedScheduleID.value)
            }
        }
    }
}
