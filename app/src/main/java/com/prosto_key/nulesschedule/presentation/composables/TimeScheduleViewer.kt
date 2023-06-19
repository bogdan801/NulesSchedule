package com.prosto_key.nulesschedule.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prosto_key.nulesschedule.domain.model.time_schedule.TimeSchedule
import com.prosto_key.nulesschedule.R

@Composable
fun TimeScheduleViewer(
    modifier: Modifier = Modifier,
    data: TimeSchedule,
    onTimeClick: (lessonId: Int, isStart: Boolean) -> Unit
) {
    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primary,
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        elevation = 5.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = stringResource(id = R.string.time_schedule_title),
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center
                )
            }
            data.lessonsTime.forEachIndexed { lessonID, currentLesson ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.2f)
                            .background(MaterialTheme.colors.surface),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "${lessonID + 1}",
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.secondary
                        )
                    }
                    Spacer(modifier = Modifier.width(1.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.4f)
                            .background(MaterialTheme.colors.surface)
                            .clickable {
                                onTimeClick(lessonID, true)
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = currentLesson.start,
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.secondary
                        )
                    }
                    Spacer(modifier = Modifier.width(1.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.4f)
                            .background(MaterialTheme.colors.surface)
                            .clickable {
                                onTimeClick(lessonID, false)
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = currentLesson.end,
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.secondary
                        )
                    }
                }
                if(lessonID != 6) Spacer(modifier = Modifier.height(1.dp))
            }
        }
    }
}