package com.prosto_key.nulesschedule.presentation.screens.subjects

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prosto_key.nulesschedule.data.datastore.readIntFromDataStore
import com.prosto_key.nulesschedule.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    private val _scheduleID = mutableStateOf(-1)
    val scheduleID: State<Int> = _scheduleID

    private val _preOpenedSubjectID = mutableStateOf(-1)
    val preOpenedSubjectID: State<Int> = _preOpenedSubjectID



    init {
        _scheduleID.value = handle.get<Int>("scheduleID") ?: -1
        _preOpenedSubjectID.value = handle.get<Int>("referredSubjectID") ?: -1

        if(_scheduleID.value == -1){
            viewModelScope.launch {
                _scheduleID.value = context.readIntFromDataStore("openedScheduleID") ?: -1
            }
        }

    }
}