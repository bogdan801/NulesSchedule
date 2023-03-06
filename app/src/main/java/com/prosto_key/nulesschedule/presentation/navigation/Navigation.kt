package com.prosto_key.nulesschedule.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.prosto_key.nulesschedule.presentation.screens.schedule.ScheduleScreen

@Composable
fun Navigation(
    navController: NavHostController
){
    NavHost(navController = navController, startDestination = Screen.ScheduleScreen.route){
        composable(Screen.ScheduleScreen.route){
            ScheduleScreen(navController = navController)
        }

        composable(
            route = Screen.ScheduleScreen.route + "/{scheduleID}",
            arguments = listOf(
                navArgument("scheduleID"){
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt("scheduleID")
            ScheduleScreen(navController = navController, scheduleID = id ?: -1)
        }

        composable(Screen.TimeScheduleScreen.route){
            //TimeScheduleScreen(navController = navController, launcher = launcher, workbook = workbook)
        }

        composable(
            route = Screen.LessonsScreen.route + "/{scheduleID}/{referredSubjectID}",
            arguments = listOf(
                navArgument("scheduleID"){
                    type = NavType.IntType
                },
                navArgument("referredSubjectID"){
                    type = NavType.IntType
                }
            )
        ){
            //LessonsScreen(navController = navController)
        }

        composable(Screen.ArchiveScreen.route){
            //ArchiveScreen(navController = navController, launcher = launcher, workbook = workbook)
        }
    }
}