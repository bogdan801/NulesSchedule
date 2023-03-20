package com.prosto_key.nulesschedule.presentation.composables

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.prosto_key.nulesschedule.domain.model.Teacher

@Composable
fun AddTeacherSheet(
    modifier: Modifier = Modifier,
    teachers: List<Teacher> = listOf(),
    onAddTeacherClick: (teacher: Teacher, isNew: Boolean) -> Unit = {_, _ ->}
) {
    val context = LocalContext.current
    var isLector by remember { mutableStateOf(true) }
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf<String?>("") }
    var email by remember { mutableStateOf<String?>("") }
    var additionalInfo by remember { mutableStateOf<String?>("") }

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Box(modifier = Modifier
            .padding(8.dp)
            .size(60.dp, 6.dp)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.onSurface)
            .align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            val lectorColor by animateColorAsState(targetValue = if (isLector) MaterialTheme.colors.secondary else MaterialTheme.colors.onSurface)
            Text(
                modifier = Modifier.clickable(
                    interactionSource = remember {MutableInteractionSource()},
                    indication = null,
                    onClick = {
                        isLector = true
                    }
                ),
                text = "ЛЕКТОР",
                style = MaterialTheme.typography.h4,
                color = lectorColor
            )
            val practicColor by animateColorAsState(targetValue = if (!isLector) MaterialTheme.colors.secondary else MaterialTheme.colors.onSurface)
            Text(
                modifier = Modifier.clickable(
                    interactionSource = remember {MutableInteractionSource()},
                    indication = null,
                    onClick = {
                        isLector = false
                    }
                ),
                text = "ПРАКТИК",
                style = MaterialTheme.typography.h4,
                color = practicColor
            )
        }

        var index by remember { mutableStateOf(0) }
        var readOnly by remember { mutableStateOf(teachers.isNotEmpty()) }
        CustomTextBox(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .height(80.dp),
            text = fullName,
            title = "ПІБ викладача",
            placeholder = "Введіть ПІБ або оберіть зі списку",
            isReadOnly = readOnly,
            selectedIndex = index,
            dropDownItems = listOf("Додати нового") + teachers.map { it.fullName },
            onTextChange = { newText ->
                fullName = newText
            },
            onDropDownItemSelected = { id, text ->
                index = id
                if(index != 0) {
                    readOnly = true
                    fullName = text
                    phoneNumber = teachers[id - 1].phoneNumber
                    email = teachers[id - 1].email
                    additionalInfo = teachers[id - 1].additionalInfo
                }
                else{
                    readOnly = false
                    fullName = ""
                    phoneNumber = ""
                    email = ""
                    additionalInfo = ""
                }
            }
        )

        CustomTextBox(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .height(80.dp),
            text = phoneNumber,
            title = "Номер телефону",
            placeholder = "Введіть номер телефону",
            isReadOnly = index != 0,
            selectedIndex = index,
            onTextChange = { newText ->
                phoneNumber = newText
            },
            keyboardType = KeyboardType.Phone
        )

        CustomTextBox(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .height(80.dp),
            text = email,
            title = "Пошта",
            placeholder = "Введіть електронну адресу",
            isReadOnly = index != 0,
            selectedIndex = index,
            onTextChange = { newText ->
                email = newText
            },
            keyboardType = KeyboardType.Email
        )

        CustomTextBox(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .height(80.dp),
            text = additionalInfo,
            title = "Додатково",
            placeholder = "Введіть додаткову інформацію",
            isReadOnly = index != 0,
            selectedIndex = index,
            onTextChange = { newText ->
                additionalInfo = newText
            },
            keyboardType = KeyboardType.Email
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Button(
                modifier = Modifier.size(160.dp, 50.dp),
                onClick = {
                    if(index == 0){
                        if(fullName.isNotBlank()){
                            val teacher = Teacher(
                                teacherID = 0,
                                fullName = fullName,
                                isLector = isLector,
                                phoneNumber = if(phoneNumber != null) {
                                    if(phoneNumber!!.isNotBlank()) phoneNumber
                                    else null
                                }
                                else null,
                                email = if(email != null) {
                                    if(email!!.isNotBlank()) email
                                    else null
                                }
                                else null,
                                additionalInfo = if(additionalInfo != null) {
                                    if(additionalInfo!!.isNotBlank()) additionalInfo
                                    else null
                                }
                                else null,
                            )
                            onAddTeacherClick(teacher, true)
                        }
                        else Toast.makeText(context, "Поле ім'я не може бути порожнім!", Toast.LENGTH_SHORT).show()
                    }
                    else onAddTeacherClick(teachers[index-1].copy(isLector = isLector), false)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSurface
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = "ДОДАТИ",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}