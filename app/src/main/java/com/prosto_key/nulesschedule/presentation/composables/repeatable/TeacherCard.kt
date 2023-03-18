package com.prosto_key.nulesschedule.presentation.composables.repeatable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        backgroundColor = MaterialTheme.colors.onPrimary
    ) {
        Box(modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
        ){
            Column(
                modifier = Modifier.padding(end = 32.dp).fillMaxSize(),
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

            val alignment = if(data.phoneNumber==null || data.email==null) Alignment.CenterEnd else Alignment.BottomEnd
            IconButton(
                modifier = Modifier.align(alignment),
                onClick = onTeacherDeleteClick
            ) {
               Icon(imageVector = Icons.Default.Close, contentDescription = "Delete teacher")
            }
        }
    }
}