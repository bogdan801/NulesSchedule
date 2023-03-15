package com.prosto_key.nulesschedule.presentation.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.prosto_key.nulesschedule.data.datastore.readIntFromDataStore
import com.prosto_key.nulesschedule.presentation.screens.schedule.ScheduleScreen
import com.prosto_key.nulesschedule.presentation.screens.time_schedule.TimeScheduleScreen
import org.apache.poi.xssf.usermodel.XSSFWorkbook

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
            //ArchiveScreen(navController = navController, launcher = launcher)
        }
    }
}