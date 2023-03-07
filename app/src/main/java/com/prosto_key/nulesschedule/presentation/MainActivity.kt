package com.prosto_key.nulesschedule.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.prosto_key.nulesschedule.data.local.database.Dao
import com.prosto_key.nulesschedule.data.local.database.Database
import com.prosto_key.nulesschedule.data.local.database.entities.TimeScheduleEntity
import com.prosto_key.nulesschedule.data.util.cutSubjectNameFromLesson
import com.prosto_key.nulesschedule.data.util.levenshtein
import com.prosto_key.nulesschedule.data.util.similarityRatio
import com.prosto_key.nulesschedule.domain.model.Lesson
import com.prosto_key.nulesschedule.domain.model.Schedule
import com.prosto_key.nulesschedule.domain.model.time_schedule.LessonTime
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.domain.model.week.Day
import com.prosto_key.nulesschedule.domain.model.week.Week
import com.prosto_key.nulesschedule.domain.repository.Repository
import com.prosto_key.nulesschedule.presentation.navigation.Navigation
import com.prosto_key.nulesschedule.presentation.theme.NulesScheduleTheme
import com.prosto_key.nulesschedule.presentation.util.LockScreenOrientation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repo: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val schedule = Schedule(
            scheduleID = 0,
            fileName = "fakultet_informaciynih_tehnologiy_03.03.23.xlsx",
            major = "КН",
            year = "4 + 2 ст",
            group = "1",
            week = Week(
                listOf(
                    Day(
                        numeratorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "В6: Технології комп'ютерного проектування 214к.15 1"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В5: Адміністрування комп'ютерних мереж 206 к.15 2"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Програмне забезпечення комп. інтегрованих технологій 214 к.15 2"
                            ),
                            null,
                            null,
                            null,
                            null
                        ),
                        denominatorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "В6: Технології комп'ютерного проектування 214к.15 1"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В5: Адміністрування комп'ютерних мереж 206 к.15 2"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Програмне забезпечення комп. інтегрованих технологій 214 к.15 2"
                            ),
                            null,
                            null,
                            null,
                            null
                        ),
                    ),
                    Day(
                        numeratorLessons = listOf(
                            null,
                            null,
                            null,
                            null,
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Засоби мультимедіа 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Цифрові технолгії в бізнесі 206 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "Технології розробки ІУС 230 к.15"
                            )
                        ),
                        denominatorLessons = listOf(
                            null,
                            null,
                            null,
                            null,
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Цифрові техн. В бізнесі 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Цифрові технолгії в бізнесі 206 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "Технології розробки ІУС 230 к.15"
                            )
                        ),
                    ),
                    Day(
                        numeratorLessons = listOf(
                            null,
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Засоби мультимедіа 211 к.15 1"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В3: Операційні системи реального часу 206 к.15 2"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В2: Програмування мобільних пристроїв 214 к.15 2"
                            ),
                            null,
                            null,
                            null
                        ),
                        denominatorLessons = listOf(
                            null,
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Засоби мультимедіа 211 к.15 1"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В3: Операційні системи реального часу 206 к.15 2"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В2: Програмування мобільних пристроїв 214 к.15 2"
                            ),
                            null,
                            null,
                            null
                        ),
                    ),
                    Day(
                        numeratorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Системи КЕЕМ 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В2: Програмування мобільних пристроїв  230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Програмне забезпечення комп. Інтегрованих технологій 232 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "Іноземна мова 132 к.11"
                            ),
                            null,
                            null,
                            null
                        ),
                        denominatorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "В5: Технології комп'ютерного проектування 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В3: Операційні системи реального часу 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В5: Адміністрування комп. мереж 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "Іноземна мова 132 к.11"
                            ),
                            null,
                            null,
                            null
                        ),
                    ),
                    Day(
                        numeratorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "Технології розробки ІУС 213 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Системи КЕЕМ 214 к.15"
                            ),
                            null,
                            null,
                            null,
                            null,
                            null
                        ),
                        denominatorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "Технології розробки ІУС 213 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Системи КЕЕМ 214 к.15"
                            ),
                            null,
                            null,
                            null,
                            null,
                            null
                        ),
                    ),
                )
            )
        )*/

        lateinit var timeSchedule: Flow<TimeSchedule>
        runBlocking {
            timeSchedule = repo.getTimeScheduleFlow()
        }

        setContent {
            LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            NulesScheduleTheme {
                //Navigation(navController = rememberNavController())
                val schedule by timeSchedule.collectAsState(initial = TimeSchedule(listOf()))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    schedule.lessonsTime.forEachIndexed { index, time ->
                        Text(text = "${index+1}. ${time.start} - ${time.end}")
                    }

                    var start by remember { mutableStateOf("")}
                    var end by remember { mutableStateOf("")}
                    var index by remember { mutableStateOf("1")}
                    TextField(modifier = Modifier.fillMaxWidth(), value = index, onValueChange = {index = it})
                    TextField(modifier = Modifier.fillMaxWidth(), value = start, onValueChange = {start = it})
                    TextField(modifier = Modifier.fillMaxWidth(), value = end, onValueChange = {end = it})

                    Button(
                        onClick = {
                            lifecycleScope.launch {
                                repo.editTimeScheduleLesson(index.toInt()-1, LessonTime(start = start, end = end))
                            }
                        }
                    ) {
                        Text(text = "Edit")
                    }
                }
            }
        }
    }
}


