package com.prosto_key.nulesschedule.presentation.composables.repeatable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LessonEntry(
    modifier: Modifier = Modifier,
    text: String,
    state: LessonState = LessonState.Unspecified,
    fraction: Float? = null,
    onLongClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(
                when (state) {
                    LessonState.Passed -> MaterialTheme.colors.onSecondary
                    LessonState.Current -> MaterialTheme.colors.primary
                    LessonState.Unspecified -> MaterialTheme.colors.surface
                }
            )
            .border(
                1.dp,
                when (state) {
                    LessonState.Passed -> MaterialTheme.colors.onBackground
                    LessonState.Current -> MaterialTheme.colors.primary
                    LessonState.Unspecified -> MaterialTheme.colors.onBackground
                }
            )
            .combinedClickable(
                onClick = {},
                onLongClick = onLongClick,
            ),
        contentAlignment = Alignment.CenterStart
    ){
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = text,
            style = MaterialTheme.typography.h6,
            color = when (state) {
                LessonState.Passed -> MaterialTheme.colors.secondary
                LessonState.Current -> MaterialTheme.colors.onPrimary
                LessonState.Unspecified -> MaterialTheme.colors.secondary
            },
            maxLines = 2
        )
        if(state == LessonState.Current && fraction!= null){
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .align(Alignment.BottomCenter)
            ) {
                Box(
                    modifier = modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction)
                        .background(MaterialTheme.colors.secondary)
                )
                Box(
                    modifier = modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(MaterialTheme.colors.surface)
                )
            }
        }
    }
}

enum class LessonState{
    Passed,
    Current,
    Unspecified
}