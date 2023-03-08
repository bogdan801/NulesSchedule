package com.prosto_key.nulesschedule.data.local.excel_parsing

import androidx.compose.runtime.MutableState
import com.prosto_key.nulesschedule.data.util.getCellValue
import com.prosto_key.nulesschedule.data.util.getLastColumnNum
import com.prosto_key.nulesschedule.data.util.getLastRowNum
import com.prosto_key.nulesschedule.domain.model.Lesson
import com.prosto_key.nulesschedule.domain.model.week.Day
import com.prosto_key.nulesschedule.domain.model.week.Week
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.lang.Integer.min

data class ScheduleFileBuffer(
    var majorRow: MutableList<String> = mutableListOf(),
    var yearRow: MutableList<String> = mutableListOf(),
    var groupRow: MutableList<String> = mutableListOf()
)

fun getMajorsFromWorkBook(workbook: XSSFWorkbook, sheet: Int, bufferState: MutableState<ScheduleFileBuffer>): List<String> {
    val majors = mutableListOf<String>()
    val allMajors = mutableListOf<String>()
    val allYears = mutableListOf<String>()
    val allGroups = mutableListOf<String>()
    for (i in 2 until getLastColumnNum(workbook, sheet)) {
        val major = getCellValue(workbook, sheet, 3, i)
        val year = getCellValue(workbook, sheet, 2, i)
        val group = getCellValue(workbook, sheet, 4, i)
        allMajors.add(major)
        allYears.add(year)
        allGroups.add(group)

        if (!majors.contains(major)) {
            majors.add(major)
        }
    }

    bufferState.value = ScheduleFileBuffer(allMajors, allYears, allGroups)

    return majors
}

fun getYearsFromMajor(
    bufferState: ScheduleFileBuffer,
    majorList: List<String>,
    majorID: Int
): List<String>{
    if(majorList.isEmpty()) return listOf()

    val majorName = majorList[majorID]
    val columnNumbersOfMajor = mutableListOf<Int>()
    for (i in bufferState.majorRow.indices) {
        val major = bufferState.majorRow[i]
        if(majorName == major){
            columnNumbersOfMajor.add(i)
        }
    }

    val years = mutableListOf<String>()
    columnNumbersOfMajor.forEach{ colNum ->
        val year = bufferState.yearRow[colNum]
        if(!years.contains(year)){
            years.add(year)
        }
    }

    return years
}

fun getGroupsFromMajorAndYear(
    bufferState: ScheduleFileBuffer,
    majorList: List<String>,
    majorID: Int,
    yearList: List<String>,
    yearID: Int
): List<String> {
    if(majorList.isEmpty() || yearList.isEmpty()) return listOf()

    val majorName = majorList[majorID]
    val yearName = yearList[yearID]

    val groups = mutableListOf<String>()
    for (i in bufferState.majorRow.indices) {
        val major = bufferState.majorRow[i]
        val year = bufferState.yearRow[i]
        if(majorName == major && yearName == year){
            val group = bufferState.groupRow[i]
            if(!groups.contains(group)){
                groups.add(group)
            }
        }
    }

    return groups
}

fun getWeek(workbook: XSSFWorkbook, sheet: Int, major: String, year: String, group: String): Week? {
    if(workbook.numberOfSheets == 0) return null
    if(workbook.numberOfSheets < sheet+1) return null
    var colNum = -1
    for (i in 2 until getLastColumnNum(workbook, sheet)) {
        val majorCell = getCellValue(workbook, sheet, 3, i)
        val yearCell = getCellValue(workbook, sheet, 2, i)
        val groupCell = getCellValue(workbook, sheet, 4, i)

        if((majorCell == major) && (yearCell == year) && (groupCell == group)){
            colNum = i
            break
        }
    }
    if(colNum == -1) return null

    val days = mutableListOf<Day>()
    for(dayID in 0..4){
        val numLessons = mutableListOf<Lesson?>()
        val denLessons = mutableListOf<Lesson?>()
        for (lessonNumber in 0..6){
            numLessons.add(null)
            denLessons.add(null)
        }
        days.add(Day(numLessons, denLessons))
    }

    val rows = min(75, getLastRowNum(workbook, sheet))
    for(i in 6..rows){
        val dayNumber = (i - 6) / 14
        val lessonNumber = ((i - 6) - (14 * dayNumber)) / 2
        val isNumerator = i % 2 == 0
        val lessonCell = getCellValue(workbook, sheet, i, colNum)
        val lesson = if(lessonCell.isBlank()) null else Lesson(0, 0, lessonCell)

        if(isNumerator) days[dayNumber].numeratorLessons[lessonNumber] = lesson
        else days[dayNumber].denominatorLessons[lessonNumber] = lesson
    }

    return Week(days = days)
}