package com.prosto_key.nulesschedule.presentation.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prosto_key.nulesschedule.data.util.*
import com.prosto_key.nulesschedule.domain.model.Lesson
import com.prosto_key.nulesschedule.domain.model.time_schedule.CurrentLesson
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.domain.model.week.Week
import com.prosto_key.nulesschedule.presentation.composables.repeatable.LessonEntry
import com.prosto_key.nulesschedule.presentation.composables.repeatable.LessonState
import kotlinx.datetime.*

@Composable
fun ScheduleViewer(
    modifier: Modifier = Modifier,
    data: Week?,
    timeSchedule: State<TimeSchedule>,
    currentDayTime: LocalDateTime = getCurrentDateTime(),
    onLessonLongClick: (lesson: Lesson?) -> Unit = {}
) {
    if(data == null){
        Box(modifier = modifier.padding(40.dp), contentAlignment = Alignment.Center){
            Text(
                text = "Натисніть на \"+\" та додайте файл розкладу",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center
            )
        }
    }
    else{
        var selectedDay by remember {
            mutableStateOf(0)
        }
        val weekName by remember {
            derivedStateOf {
                selectedDay.getDayOfWeekName()
            }
        }
        val selectedDate by remember {
            derivedStateOf {
                currentDayTime.date + DatePeriod(days = if(currentDayTime.dayOfWeek.ordinal >= 5) 7 + (selectedDay - currentDayTime.dayOfWeek.ordinal) else (selectedDay - currentDayTime.dayOfWeek.ordinal))
            }
        }
        var currentLesson by remember {
            mutableStateOf(
                timeSchedule.value.getCurrentLesson(currentDayTime)
            )
        }

        var pastLesson by remember {
            mutableStateOf(
                timeSchedule.value.pastLesson(currentDayTime)
            )
        }

        LaunchedEffect(key1 = timeSchedule.value){
            currentLesson = timeSchedule.value.getCurrentLesson(currentDayTime)
            pastLesson = timeSchedule.value.pastLesson(currentDayTime)
        }

        LaunchedEffect(key1 = currentDayTime){
            currentLesson = timeSchedule.value.getCurrentLesson(currentDayTime)
            pastLesson = timeSchedule.value.pastLesson(currentDayTime)
        }

        var isNumerator by remember { mutableStateOf(true) }

        Column(modifier = modifier) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 6.dp
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = weekName.uppercase(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.onPrimary
                        )
                        Text(
                            text = selectedDate.asFormattedString(),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(MaterialTheme.colors.surface)
                    ) {
                        val lessons = if(isNumerator) data.days[selectedDay].numeratorLessons else data.days[selectedDay].denominatorLessons
                        lessons.forEachIndexed { index, lesson ->
                            var state = LessonState.Unspecified

                            if(currentDayTime.date == selectedDate){
                                state = if(index<=pastLesson) LessonState.Passed else LessonState.Unspecified
                                if(currentLesson!=null) {
                                    if(currentLesson!!.lessonNum == index) state = LessonState.Current
                                }
                            }

                            LessonEntry(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                text = "${index+1}. " + (lesson?.lessonName ?: "-"),
                                state = state,
                                fraction = if(currentLesson!=null) currentLesson!!.fractionPassed else null,
                                onLongClick = {
                                    onLessonLongClick(lesson)
                                }
                            )
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                        contentAlignment = Alignment.CenterStart
                    ){
                        Text(text = currentLesson.toString())
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 6.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    (0..4).forEach {
                        Text(
                            modifier = Modifier.clickable { selectedDay = it },
                            text = it.toString()
                        )
                    }
                }
            }
        }
    }
}