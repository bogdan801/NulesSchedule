package com.prosto_key.nulesschedule.presentation.composables.repeatable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    icon: @Composable BoxScope.() -> Unit,
    title: String = "",
    onItemClick: () -> Unit = {}
) {
    Row(modifier = modifier
        .height(56.dp)
        .clickable(onClick = onItemClick)
        .padding(vertical = 8.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxHeight()
            .width(64.dp),
            contentAlignment = Alignment.Center
        ){
            icon()
        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .weight(1f),
            contentAlignment = Alignment.CenterStart
        ){
            Text(
                text = title,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}