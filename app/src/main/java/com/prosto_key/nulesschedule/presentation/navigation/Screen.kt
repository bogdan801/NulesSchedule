package com.prosto_key.nulesschedule.presentation.navigation

sealed class Screen(val route: String) {
    object ScheduleScreen  : Screen("schedule")
    object TimeScheduleScreen : Screen("timeSchedule")
    object LessonsScreen  : Screen("lessons")
    object ArchiveScreen    : Screen("archive")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}