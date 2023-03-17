package com.prosto_key.nulesschedule.data.util

import kotlinx.datetime.*
import java.time.DayOfWeek

fun getCurrentDateTime() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDate.asFormattedString() = "${dayOfMonth.toString().padStart(2, '0')}.${monthNumber.toString().padStart(2, '0')}.$year"

fun LocalDateTime.dayOfWeekToString(): String = when(this.dayOfWeek){
    DayOfWeek.MONDAY    -> "Понеділок"
    DayOfWeek.TUESDAY   -> "Вівторок"
    DayOfWeek.WEDNESDAY -> "Середа"
    DayOfWeek.THURSDAY  -> "Четвер"
    DayOfWeek.FRIDAY    -> "П'ятниця"
    DayOfWeek.SATURDAY  -> "Субота"
    DayOfWeek.SUNDAY    -> "Неділя"
}

fun Int.getDayOfWeekName(short: Boolean = false) = if(!short){
    when(this % 7){
        0 -> "Понеділок"
        1 -> "Вівторок"
        2 -> "Середа"
        3 -> "Четвер"
        4 -> "П'ятниця"
        5 -> "Субота"
        6 -> "Неділя"
        else -> ""
    }
}
else{
    when(this % 7){
        0 -> "ПН"
        1 -> "ВТ"
        2 -> "СР"
        3 -> "ЧТ"
        4 -> "ПТ"
        5 -> "СБ"
        6 -> "НД"
        else -> ""
    }
}

fun String.timeToLocalDateTime(): LocalDateTime? {
    val parts = this.split(":")
    if(parts.size != 2) return null
    val currentDayTime  = getCurrentDateTime()
    return LocalDateTime(year = currentDayTime.year, monthNumber = currentDayTime.monthNumber, dayOfMonth = currentDayTime.dayOfMonth, hour = parts[0].toInt(), minute = parts[1].toInt())
}

fun LocalDateTime.isTimeBetween(start: LocalDateTime, end: LocalDateTime, isInclusive: Boolean = false): Boolean{
    return if(isInclusive){
        this in start..end
    }
    else this>=start && this<end
}

fun LocalDateTime.getFractionBetween(start: LocalDateTime, end: LocalDateTime): Float {
    if(this<start) return 0f
    if(this>end) return 1f
    val startInstant = start.toInstant(TimeZone.currentSystemDefault())
    val endInstant = end.toInstant(TimeZone.currentSystemDefault())
    val currentInstant = this.toInstant(TimeZone.currentSystemDefault())
    val wholeTimeDistance = startInstant.periodUntil(endInstant, TimeZone.currentSystemDefault())
    val wholeDistanceInMinutes = (wholeTimeDistance.hours * 60) + wholeTimeDistance.minutes
    val passedDistance = startInstant.periodUntil(currentInstant, TimeZone.currentSystemDefault())
    val passedDistanceInMinutes = (passedDistance.hours * 60) + passedDistance.minutes
    return passedDistanceInMinutes.toFloat() / wholeDistanceInMinutes.toFloat()
}

fun LocalDateTime.toTimeString() = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"