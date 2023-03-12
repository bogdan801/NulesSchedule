package com.prosto_key.nulesschedule.domain.model

import com.prosto_key.nulesschedule.domain.model.week.Week

data class Schedule(
    val scheduleID: Int = 0,
    val fileName: String = "",
    val major: String = "",
    val year: String = "",
    val group: String = "",
    val week: Week? = null
){
    override fun equals(other: Any?): Boolean {
        val another = other as Schedule
        return this.fileName == another.fileName && this.major == another.major && this.year == another.year && this.group == another.group
    }

    override fun hashCode(): Int {
        var result = scheduleID
        result = 31 * result + fileName.hashCode()
        result = 31 * result + major.hashCode()
        result = 31 * result + year.hashCode()
        result = 31 * result + group.hashCode()
        result = 31 * result + (week?.hashCode() ?: 0)
        return result
    }
}
