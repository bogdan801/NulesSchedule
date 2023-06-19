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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.prosto_key.nulesschedule.R
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
                text = stringResource(id = R.string.lecturer),
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
                text = stringResource(id = R.string.practitioner),
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
            title = stringResource(id = R.string.professors_full_name),
            placeholder = stringResource(id = R.string.enter_full_name),
            isReadOnly = readOnly,
            selectedIndex = index,
            dropDownItems = listOf(stringResource(id = R.string.add_new)) + teachers.map { it.fullName },
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
            title = stringResource(id = R.string.phone_number),
            placeholder = stringResource(id = R.string.enter_phone_number),
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
            title = stringResource(id = R.string.email),
            placeholder = stringResource(id = R.string.enter_email),
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
            title = stringResource(id = R.string.additionally),
            placeholder = stringResource(id = R.string.enter_additional_info),
            isReadOnly = index != 0,
            selectedIndex = index,
            onTextChange = { newText ->
                if(newText.length <= 40){
                    additionalInfo = newText
                }
                else Toast.makeText(context, context.getString(R.string.max_characters), Toast.LENGTH_SHORT).show()

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
                        else Toast.makeText(context, context.getString(R.string.name_cant_be_empty), Toast.LENGTH_SHORT).show()
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
                    text = stringResource(id = R.string.add),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}