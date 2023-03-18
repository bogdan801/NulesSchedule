package com.prosto_key.nulesschedule.presentation.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.prosto_key.nulesschedule.presentation.screens.schedule.ScheduleScreen
import com.prosto_key.nulesschedule.presentation.screens.subjects.SubjectsScreen
import com.prosto_key.nulesschedule.presentation.screens.time_schedule.TimeScheduleScreen

@Composable
fun Navigation(
    navController: NavHostController,
    launcher: ActivityResultLauncher<Intent>
){
    NavHost(navController = navController, startDestination = Screen.ScheduleScreen.route){
        composable(Screen.ScheduleScreen.route){
            ScheduleScreen(navController = navController, launcher = launcher)
        }

        composable(
            route = Screen.ScheduleScreen.route + "/{scheduleID}",
            arguments = listOf(
                navArgument("scheduleID"){
                    type = NavType.IntType
                }
            )
        ){
            ScheduleScreen(navController = navController, launcher = launcher)
        }

        composable(Screen.TimeScheduleScreen.route){
            TimeScheduleScreen(navController = navController)
        }

        composable(
            route = Screen.SubjectsScreen.route + "/{scheduleID}/{referredSubjectID}",
            arguments = listOf(
                navArgument("scheduleID"){
                    type = NavType.IntType
                },
                navArgument("referredSubjectID"){
                    type = NavType.IntType
                }
            )
        ){
            SubjectsScreen(navController = navController)
        }

        composable(Screen.ArchiveScreen.route){
            //ArchiveScreen(navController = navController, launcher = launcher)
        }
    }
}