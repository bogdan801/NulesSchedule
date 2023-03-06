package com.prosto_key.nulesschedule.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.prosto_key.nulesschedule.data.local.database.Dao
import com.prosto_key.nulesschedule.data.local.database.Database
import com.prosto_key.nulesschedule.data.local.database.entities.TimeScheduleEntity
import com.prosto_key.nulesschedule.presentation.navigation.Navigation
import com.prosto_key.nulesschedule.presentation.theme.NulesScheduleTheme
import com.prosto_key.nulesschedule.presentation.util.LockScreenOrientation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var schedule: List<TimeScheduleEntity>
        runBlocking {
            schedule = database.dbDao.getTimeScheduleEntities().first()
        }

        setContent {
            LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            NulesScheduleTheme {
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        schedule.forEach { timeEntity ->
                            Text("${timeEntity.lessonNumber}   ${timeEntity.startTime}    ${timeEntity.endTime}")
                        }
                    }
                }*/

                Navigation(navController = rememberNavController())
            }
        }
    }
}


