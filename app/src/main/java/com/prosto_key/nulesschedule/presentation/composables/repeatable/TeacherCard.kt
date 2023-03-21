package com.prosto_key.nulesschedule.presentation.composables.repeatable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.prosto_key.nulesschedule.domain.model.Teacher

@Composable
fun TeacherCard(
    modifier: Modifier = Modifier,
    onTeacherDeleteClick: () -> Unit = {},
    data: Teacher
) {

    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.onPrimary,
        shape = RectangleShape,
        elevation = 2.dp
    ) {
        Box(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ){
            Column(
                modifier = Modifier.padding(end = 32.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                if(data.isLector != null) {
                    Text(
                        text = if (data.isLector) " Лектор" else "Практик",
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.h5
                    )
                }
                Text(
                    text = data.fullName,
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.body1
                )
                if(data.phoneNumber != null) {
                    Text(
                        text = data.phoneNumber,
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                }
                if(data.email != null) {
                    Text(
                        text = data.email,
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                }
                if(data.additionalInfo != null) {
                    Text(
                        text = data.additionalInfo,
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                }
            }

            IconButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = onTeacherDeleteClick
            ) {
               Icon(imageVector = Icons.Default.Close, contentDescription = "Delete teacher")
            }
        }
    }
}