package com.prosto_key.nulesschedule.presentation.screens.schedule

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prosto_key.nulesschedule.data.datastore.readIntFromDataStore
import com.prosto_key.nulesschedule.data.datastore.saveIntToDataStore
import com.prosto_key.nulesschedule.data.local.excel_parsing.WorkBook
import com.prosto_key.nulesschedule.data.util.getCurrentDateTime
import com.prosto_key.nulesschedule.domain.model.Schedule
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel
@Inject
constructor(
    private val repository: Repository,
    handle: SavedStateHandle,
    application: Application,
    private val workBookState: MutableState<WorkBook>
): AndroidViewModel(application) {
    //CONTEXT
    private val context
        get() = getApplication<Application>()

    val navScheduleIDArgument = handle.get<Int>("scheduleID") ?: -1

    fun clearWorkBookState(){
        workBookState.value = WorkBook(workBook = XSSFWorkbook())
    }

    //SCHEDULE
    private val _selectedScheduleID =  mutableStateOf(-1)
    val selectedScheduleID: State<Int> =  _selectedScheduleID

    val schedule =  mutableStateOf(Schedule())

    val week by derivedStateOf {
        schedule.value.week
    }

    //OPEN FILE SHEET
    private val _showAddSheet = mutableStateOf(false)
    val showAddSheet: State<Boolean> = _showAddSheet

    fun setShowAddSheetState(show: Boolean){
        _showAddSheet.value = show
    }


    val fileName = derivedStateOf { workBookState.value.fileName }

    fun openFile(launcher: ActivityResultLauncher<Intent>){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        }

        launcher.launch(intent)
    }

    val majors = derivedStateOf {
        if(workBookState.value.workBook.numberOfSheets > 0) repository.readMajorsFromFile(workBookState.value.workBook, 0)
        else listOf()
    }

    private val _selectedMajor =  mutableStateOf(0)
    val selectedMajor: State<Int> =  _selectedMajor

    fun selectMajor(id: Int){
        _selectedYear.value = 0
        _selectedGroup.value = 0
        _selectedMajor.value = id
    }

    val years = derivedStateOf {
        if(workBookState.value.workBook.numberOfSheets > 0) repository.readYearsOfMajor(majors.value, selectedMajor.value)
        else listOf()
    }

    private val _selectedYear =  mutableStateOf(0)
    val selectedYear: State<Int> =  _selectedYear

    fun selectYear(id: Int){
        _selectedGroup.value = 0
        _selectedYear.value = id
    }

    val groups = derivedStateOf {
        if(workBookState.value.workBook.numberOfSheets > 0) {
            repository.readGroupsFromMajorAndYear(
                majors.value,
                selectedMajor.value,
                years.value,
                selectedYear.value
            )
        }
        else listOf()
    }

    private val _selectedGroup =  mutableStateOf(0)
    val selectedGroup: State<Int> =  _selectedGroup

    fun selectGroup(id: Int){
        _selectedGroup.value = id
    }

    fun selectSchedule(){
        if(majors.value.isEmpty() || years.value.isEmpty() || groups.value.isEmpty()) {
            Toast.makeText(context, "Розклад не обрано!", Toast.LENGTH_SHORT).show()
            return
        }
        val week = repository.readWeekFromFile(
            workbook = workBookState.value.workBook,
            sheet = 0,
            major = majors.value[selectedMajor.value],
            year = years.value[selectedYear.value],
            group = groups.value[selectedGroup.value]
        )

        schedule.value = Schedule(
            fileName = workBookState.value.fileName,
            major = majors.value[selectedMajor.value],
            year = years.value[selectedYear.value],
            group = groups.value[selectedGroup.value],
            week = week
        )

        viewModelScope.launch {
            if(repository.isScheduleUnique(schedule.value)){
                val newScheduleId = repository.insertSchedule(schedule.value)
                context.saveIntToDataStore("openedScheduleID", newScheduleId)
                _selectedScheduleID.value = newScheduleId
            }
            else {
                val scheduleIDInDB = repository.getScheduleID(schedule.value)
                context.saveIntToDataStore("openedScheduleID", scheduleIDInDB)
                _selectedScheduleID.value = scheduleIDInDB
            }
        }

        Toast.makeText(context, "Розклад відкрито і збережено", Toast.LENGTH_SHORT).show()
    }

    //SCHEDULE DISPLAY
    private val _currentDateTime =  mutableStateOf(getCurrentDateTime())
    val currentDateTime: State<LocalDateTime> = _currentDateTime

    private val _timeSchedule =  mutableStateOf(TimeSchedule(listOf()))
    val timeSchedule: State<TimeSchedule> = _timeSchedule

    init {
        //selected value
        val currentScheduleId = handle.get<Int>("scheduleID") ?: -1
        //if selected value = -1 (app just launched)
        if(currentScheduleId < 0){
            //reading from datastore
            runBlocking{
                _selectedScheduleID.value = context.readIntFromDataStore("openedScheduleID") ?: -1
            }
        }
        else{
            _selectedScheduleID.value = currentScheduleId
        }

        //reading schedule from database
        if(_selectedScheduleID.value >= 0){
            println(_selectedScheduleID.value.toString())
            viewModelScope.launch {
                schedule.value = repository.getFullSchedule(_selectedScheduleID.value)
            }
        }

        //datetime update once a minute
        viewModelScope.launch {
            while(true){
                delay((60 - _currentDateTime.value.second) * 1000L)
                _currentDateTime.value = getCurrentDateTime()
            }
        }

        //reading timeschedule from database
        viewModelScope.launch {
            _timeSchedule.value = repository.getTimeScheduleFlow().first()
        }
    }
}
