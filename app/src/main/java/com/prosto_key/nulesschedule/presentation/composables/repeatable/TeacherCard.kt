package com.prosto_key.nulesschedule.presentation.composables.repeatable

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.prosto_key.nulesschedule.domain.model.Teacher

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TeacherCard(
    modifier: Modifier = Modifier,
    onTeacherDeleteClick: () -> Unit = {},
    data: Teacher
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
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
                modifier = Modifier
                    .padding(end = 32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                if(data.isLector != null) {
                    Text(
                        text = if (data.isLector) "Лектор" else "Практик",
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.h5
                    )
                }
                Text(
                    modifier = Modifier.combinedClickable(
                        onClick = {},
                        onLongClick = {
                            clipboardManager.setText(AnnotatedString(data.fullName))
                            Toast.makeText(context, "Ім'я \"${data.fullName}\" скопійовано", Toast.LENGTH_SHORT).show()
                        },
                    ),
                    text = data.fullName,
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.body1
                )
                if(data.phoneNumber != null) {
                    Text(
                        modifier = Modifier.combinedClickable(
                            onClick = {},
                            onLongClick = {
                                clipboardManager.setText(AnnotatedString(data.phoneNumber))
                                Toast.makeText(context, "Номер \"${data.phoneNumber}\" скопійовано", Toast.LENGTH_SHORT).show()
                            },
                        ),
                        text = data.phoneNumber,
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                }
                if(data.email != null) {
                    Text(
                        modifier = Modifier.combinedClickable(
                            onClick = {},
                            onLongClick = {
                                clipboardManager.setText(AnnotatedString(data.email))
                                Toast.makeText(context, "Пошту \"${data.email}\" скопійовано", Toast.LENGTH_SHORT).show()
                            },
                        ),
                        text = data.email,
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                }
                if(data.additionalInfo != null) {
                    Text(
                        modifier = Modifier.combinedClickable(
                            onClick = {},
                            onLongClick = {
                                clipboardManager.setText(AnnotatedString(data.additionalInfo))
                                Toast.makeText(context, "\"${data.email}\" скопійовано", Toast.LENGTH_SHORT).show()
                            },
                        ),
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