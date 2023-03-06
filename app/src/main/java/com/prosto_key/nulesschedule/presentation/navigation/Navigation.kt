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
    NavHost(navController = navController, startDestination = Screen.ScheduleScreen.route + "0"){
        composable(
            route = Screen.ScheduleScreen.route + "/{scheduleID}",
            arguments = listOf(
                navArgument("scheduleID"){
                    type = NavType.IntType
                }
            )
        ){
            ScheduleScreen(navController = navController)
        }

        composable(Screen.TimeScheduleScreen.route){
            //TimeScheduleScreen(navController = navController, launcher = launcher, workbook = workbook)
        }

        composable(
            route = Screen.LessonsScreen.route + "/{scheduleID}",
            arguments = listOf(
                navArgument("scheduleID"){
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