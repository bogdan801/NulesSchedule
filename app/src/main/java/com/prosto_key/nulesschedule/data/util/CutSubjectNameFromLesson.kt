package com.prosto_key.nulesschedule.data.util

fun cutSubjectNameFromLesson(lessonName: String): String {
    val regex = """\d{1,3} ?к\. ?\d{1,2}([\n ]\d)?""".toRegex()
    val match = regex.find(lessonName)
    return if(match != null){
        lessonName.removeRange(match.range)
    }
    else lessonName
}
