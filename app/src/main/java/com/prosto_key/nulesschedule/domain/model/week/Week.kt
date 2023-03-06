package com.prosto_key.nulesschedule.domain.model.week

data class Week(
    private val days: List<Day>
){
    fun getDay(isNumerator: Boolean, dayOfWeek: DayOfWeek) =
        if(isNumerator) days[dayOfWeek.ordinal].numeratorLessons
        else days[dayOfWeek.ordinal].denominatorLessons
}

enum class DayOfWeek{
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday
}
