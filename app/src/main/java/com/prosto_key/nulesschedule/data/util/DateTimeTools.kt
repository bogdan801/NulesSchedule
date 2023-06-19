package com.prosto_key.nulesschedule.data.util

import android.content.Context
import com.prosto_key.nulesschedule.R
import kotlinx.datetime.*
import java.time.DayOfWeek

fun getCurrentDateTime() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDate.asFormattedString() = "${dayOfMonth.toString().padStart(2, '0')}.${monthNumber.toString().padStart(2, '0')}.$year"

fun LocalDateTime.dayOfWeekToString(context: Context): String = when(this.dayOfWeek){
    DayOfWeek.MONDAY    -> context.getString(R.string.monday)
    DayOfWeek.TUESDAY   -> context.getString(R.string.tuesday)
    DayOfWeek.WEDNESDAY -> context.getString(R.string.wednesday)
    DayOfWeek.THURSDAY  -> context.getString(R.string.thursday)
    DayOfWeek.FRIDAY    -> context.getString(R.string.friday)
    DayOfWeek.SATURDAY  -> context.getString(R.string.saturday)
    DayOfWeek.SUNDAY    -> context.getString(R.string.sunday)
}

fun Int.getDayOfWeekName(short: Boolean = false, context: Context) = if(!short){
    when(this % 7){
        0 -> context.getString(R.string.monday)
        1 -> context.getString(R.string.tuesday)
        2 -> context.getString(R.string.wednesday)
        3 -> context.getString(R.string.thursday)
        4 -> context.getString(R.string.friday)
        5 -> context.getString(R.string.saturday)
        6 -> context.getString(R.string.sunday)
        else -> ""
    }
}
else{
    when(this % 7){
        0 -> context.getString(R.string.mo)
        1 -> context.getString(R.string.tu)
        2 -> context.getString(R.string.we)
        3 -> context.getString(R.string.th)
        4 -> context.getString(R.string.fr)
        5 -> context.getString(R.string.sa)
        6 -> context.getString(R.string.su)
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