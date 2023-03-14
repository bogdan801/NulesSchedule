package com.prosto_key.nulesschedule.domain.model.time_schedule

import com.prosto_key.nulesschedule.data.util.getFractionBetween
import com.prosto_key.nulesschedule.data.util.isTimeBetween
import com.prosto_key.nulesschedule.data.util.timeToLocalDateTime
import kotlinx.datetime.LocalDateTime

data class TimeSchedule(
    val lessonsTime: List<LessonTime>
){
    fun getCurrentLesson(currentTime: LocalDateTime): CurrentLesson?{
        var currentLesson: CurrentLesson?  = null

        for(index in lessonsTime.indices){
            if(currentTime.isTimeBetween(lessonsTime[index].start.timeToLocalDateTime()!!, lessonsTime[index].end.timeToLocalDateTime()!!)){

                println()
                currentLesson = CurrentLesson(
                    lessonNum = index,
                    lesson = lessonsTime[index],
                    fractionPassed = currentTime.getFractionBetween(lessonsTime[index].start.timeToLocalDateTime()!!, lessonsTime[index].end.timeToLocalDateTime()!!)
                )
                break
            }
        }

        return currentLesson
    }

    fun pastLesson(currentTime: LocalDateTime): Int{
        var passed = -1
        lessonsTime.forEachIndexed { index, time ->
            if(currentTime>=time.end.timeToLocalDateTime()!!) passed = index
        }
        return passed
    }
}

data class CurrentLesson(
    val lessonNum: Int = 0,
    val lesson: LessonTime?,
    val fractionPassed: Float
)