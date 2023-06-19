package com.prosto_key.nulesschedule.presentation.composables.repeatable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.prosto_key.nulesschedule.R
import com.prosto_key.nulesschedule.domain.model.Schedule

@Composable
fun ScheduleCard(
    modifier: Modifier = Modifier,
    data: Schedule,
    isSelected: Boolean = false,
    onCardClick: () -> Unit = {},
    onScheduleDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        backgroundColor = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onCardClick)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .shadow(elevation = 10.dp)
                    .background(if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f),
                    text = data.fileName,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(80.dp),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(onClick = onScheduleDeleteClick) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
            
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.major),
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            text = data.major,
                            color = MaterialTheme.colors.secondary,
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxHeight()
                        .width(1.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colors.onSurface)
                )
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.year),
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            text = data.year,
                            color = MaterialTheme.colors.secondary,
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxHeight()
                        .width(1.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colors.onSurface)
                )
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.group),
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            text = data.group,
                            color = MaterialTheme.colors.secondary,
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
            }
        }
    }
}