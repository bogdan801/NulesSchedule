package com.prosto_key.nulesschedule.presentation.screens.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.prosto_key.nulesschedule.presentation.navigation.Screen

@Composable
fun ScheduleScreen(
    navController: NavHostController,
    scheduleID: Int = -1
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column{
            Text(scheduleID.toString())
            Button(onClick = {
                navController.navigate(Screen.ScheduleScreen.withArgs("${scheduleID+1}"))
            }) {
                Text("Go to ${scheduleID+1}")
            }
        }
    }
}