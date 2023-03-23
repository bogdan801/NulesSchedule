package com.prosto_key.nulesschedule.presentation.screens.time_schedule

import android.app.Application
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prosto_key.nulesschedule.data.datastore.readIntFromDataStore
import com.prosto_key.nulesschedule.data.util.isTimeBetween
import com.prosto_key.nulesschedule.data.util.timeToLocalDateTime
import com.prosto_key.nulesschedule.data.util.toTimeString
import com.prosto_key.nulesschedule.domain.model.time_schedule.LessonTime
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeScheduleViewModel
@Inject
constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {
    //CONTEXT
    private val context
        get() = getApplication<Application>()

    //TIME-SCHEDULE
    private val _timeSchedule = mutableStateOf(TimeSchedule(listOf()))
    val timeSchedule: State<TimeSchedule> =  _timeSchedule

    fun chooseNewTime(lessonID: Int, isStart: Boolean, context: Context){
        val currentDateTime = if(isStart) {
            _timeSchedule.value.lessonsTime[lessonID].start.timeToLocalDateTime()!!
        }
        else{
            _timeSchedule.value.lessonsTime[lessonID].end.timeToLocalDateTime()!!
        }

        TimePickerDialog(context, { _, hour, minute ->
            val newTime = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"

            val previousTime = when{
                lessonID == 0 && isStart -> {
                    "00:00".timeToLocalDateTime()!!
                }
                isStart -> {
                    _timeSchedule.value.lessonsTime[lessonID - 1].end.timeToLocalDateTime()!!
                }
                else -> _timeSchedule.value.lessonsTime[lessonID].start.timeToLocalDateTime()!!
            }
            val nextTime = when{
                lessonID == 6 && !isStart -> {
                    "23:59".timeToLocalDateTime()!!
                }
                isStart -> {
                    _timeSchedule.value.lessonsTime[lessonID].end.timeToLocalDateTime()!!
                }
                else -> _timeSchedule.value.lessonsTime[lessonID + 1].start.timeToLocalDateTime()!!
            }
            if(newTime.timeToLocalDateTime()!!.isTimeBetween(previousTime, nextTime, isInclusive = true)){
                viewModelScope.launch {
                    val prevLessonTime = _timeSchedule.value.lessonsTime[lessonID]
                    repository.editTimeScheduleLesson(
                        lessonNumber = lessonID,
                        newTime = LessonTime(
                            start = if(isStart) newTime else prevLessonTime.start,
                            end = if(!isStart) newTime else prevLessonTime.end,
                        )
                    )
                }
            }
            else{
                Toast.makeText(context, "Не збережено! Новий час мусить бути у таких рамках: (${previousTime.toTimeString()}-${nextTime.toTimeString()})", Toast.LENGTH_SHORT).show()
            }
        }, currentDateTime.hour, currentDateTime.minute, true).show()
    }

    private val _isEditMode = mutableStateOf(false)
    val isEditMode: State<Boolean> = _isEditMode

    fun setEditMode(value: Boolean) {
        _isEditMode.value = value
    }

    private val _areSchedulesOpen = mutableStateOf(false)
    val areSchedulesOpen: State<Boolean> = _areSchedulesOpen

    init {
        viewModelScope.launch {
            _areSchedulesOpen.value = (context.readIntFromDataStore("openedScheduleID") ?: -1) >= 0
        }

        viewModelScope.launch {
            repository.getTimeScheduleFlow().collect{ newSchedule ->
                _timeSchedule.value = newSchedule
            }
        }
    }
}