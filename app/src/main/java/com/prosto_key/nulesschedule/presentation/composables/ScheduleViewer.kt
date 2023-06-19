package com.prosto_key.nulesschedule.presentation.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prosto_key.nulesschedule.R
import com.prosto_key.nulesschedule.data.datastore.readIntFromDataStore
import com.prosto_key.nulesschedule.data.datastore.saveIntToDataStore
import com.prosto_key.nulesschedule.data.util.*
import com.prosto_key.nulesschedule.domain.model.Lesson
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.domain.model.week.Week
import com.prosto_key.nulesschedule.presentation.composables.repeatable.LessonEntry
import com.prosto_key.nulesschedule.presentation.composables.repeatable.LessonState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.*

@OptIn(ExperimentalAnimationApi::class)
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
                text = stringResource(id = R.string.press_plus),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center
            )
        }
    }
    else{
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        var selectedDay by remember {
            val currentDayOfWeek = currentDayTime.dayOfWeek.ordinal
            mutableStateOf(if(currentDayOfWeek in 0..4) currentDayOfWeek else 0)
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

        var isNumerator by remember {
            var value: Boolean
            runBlocking {
                value = (context.readIntFromDataStore("isNumerator") ?: 1) > 0
            }
            mutableStateOf(value)
        }

        var slideRight by remember { mutableStateOf(true) }

        var showCurrentLessons by remember { mutableStateOf(currentDayTime.date == selectedDate) }



        Column(modifier = modifier) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 5.dp
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    AnimatedContent(
                        targetState = selectedDay,
                        transitionSpec = {
                            fadeIn() with fadeOut()
                        }
                    ) { day ->
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = day.getDayOfWeekName(context = context).uppercase(),
                                style = MaterialTheme.typography.h1,
                                color = MaterialTheme.colors.onPrimary
                            )
                            Text(
                                text = selectedDate.asFormattedString(),
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }


                    AnimatedContent(
                        targetState = selectedDay,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        transitionSpec = {
                            slideInHorizontally(
                                initialOffsetX = {
                                    if(slideRight) -it
                                    else it
                                }
                            ) with slideOutHorizontally(
                                targetOffsetX = {
                                    if(slideRight) it
                                    else -it
                                }
                            )
                        }
                    ){ currentDay ->
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(MaterialTheme.colors.surface)
                        ) {
                            val lessons = if(isNumerator) data.days[currentDay].numeratorLessons else data.days[currentDay].denominatorLessons
                            lessons.forEachIndexed { index, lesson ->
                                var state = LessonState.Unspecified

                                if(showCurrentLessons){
                                    state = if(index<=pastLesson) LessonState.Passed else LessonState.Unspecified
                                    if(currentLesson!=null) {
                                        if(currentLesson!!.lessonNum == index) state = LessonState.Current
                                    }
                                }

                                LessonEntry(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    text = "${index+1}. " + (lesson?.lessonName?.replace("\n", " ") ?: "-"),
                                    state = state,
                                    fraction = if(currentLesson!=null) currentLesson!!.fractionPassed else null,
                                    onLongClick = {
                                        onLessonLongClick(lesson)
                                    }
                                )
                            }
                        }
                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                        contentAlignment = Alignment.CenterStart
                    ){
                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .width(140.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (isNumerator) stringResource(id = R.string.numerator) else stringResource(id = R.string.denominator),
                                color = MaterialTheme.colors.onPrimary,
                                style = MaterialTheme.typography.h5
                            )
                            CustomSwitch(
                                switchState = isNumerator,
                                onStateChange = {
                                    isNumerator = !isNumerator
                                    scope.launch {
                                        context.saveIntToDataStore("isNumerator", if(isNumerator) 1 else 0)
                                    }
                                }
                            )
                        }
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
                elevation = 5.dp
            ) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val width = maxWidth
                    var offset by remember {
                        mutableStateOf(width/5 * selectedDay)
                    }
                    Row(modifier = Modifier.fillMaxSize()) {
                        (0..4).forEach { dayID ->
                            Box(modifier = Modifier
                                .fillMaxHeight()
                                .width(width / 5)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    slideRight = selectedDay > dayID
                                    selectedDay = dayID
                                    offset = (width / 5) * selectedDay
                                    scope.launch {
                                        showCurrentLessons = false
                                        delay(400)
                                        showCurrentLessons = currentDayTime.date == selectedDate
                                    }
                                },
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = dayID.getDayOfWeekName(short = true, context = context),
                                    color = MaterialTheme.colors.onPrimary,
                                    style = MaterialTheme.typography.h5
                                )
                            }
                        }
                    }
                    val animatedOffset by animateDpAsState(
                        targetValue = offset,
                        animationSpec = tween(200)
                    )
                    Card(
                        modifier = Modifier
                            .offset(x = animatedOffset)
                            .fillMaxHeight()
                            .width(width / 5)
                            .padding(horizontal = 4.dp, vertical = 5.dp),
                        shape = MaterialTheme.shapes.large,
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        elevation = 5.dp
                    ){
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            AnimatedContent(
                                targetState = selectedDay,
                                transitionSpec = {
                                    fadeIn() with fadeOut()
                                }
                            ) { dayID ->
                                Text(
                                    text = dayID.getDayOfWeekName(short = true, context = context),
                                    color = MaterialTheme.colors.secondary,
                                    style = MaterialTheme.typography.h3
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}