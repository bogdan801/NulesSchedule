package com.prosto_key.nulesschedule.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextBox(
    modifier: Modifier = Modifier,
    text: String? = "",
    onTextChange: ((newText: String) -> Unit)? = null,
    title: String = "",
    isReadOnly: Boolean = onTextChange == null,
    placeholder: String = "",
    dropDownItems: List<String> = listOf(),
    selectedIndex: Int = 0,
    onDropDownItemSelected: (index: Int, text: String) -> Unit = { _: Int, _: String -> },
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Column(modifier = modifier) {
        if(title.isNotBlank()){
            Text(
                modifier = Modifier.padding(vertical = 2.dp),
                text = title,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.secondary
            )
        }
        Box{
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    }
                    .clickable {
                        if (dropDownItems.isNotEmpty()) expanded = !expanded
                    },
                shape = MaterialTheme.shapes.small,
                backgroundColor = if(!isReadOnly) MaterialTheme.colors.onPrimary else MaterialTheme.colors.surface,
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary)
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                ){
                    if(!isReadOnly){
                        BasicTextField(
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterStart),
                            value = text ?: "",
                            onValueChange = onTextChange ?: {},
                            textStyle = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.secondary),
                            maxLines = 1,
                            singleLine = true,
                            decorationBox = { innerTextField ->
                                Box(contentAlignment = Alignment.CenterStart) {
                                    if (text!=null && text.isEmpty()) {
                                        Text(
                                            modifier = Modifier.padding(start = 4.dp),
                                            text = placeholder,
                                            style = MaterialTheme.typography.h4,
                                            color = MaterialTheme.colors.secondaryVariant
                                        )
                                    }
                                    else innerTextField()
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                            keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()})
                        )
                    }
                    else{
                        if(selectedIndex == 0 && dropDownItems.isNotEmpty()){
                            Text(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .align(Alignment.CenterStart),
                                text = placeholder,
                                style = MaterialTheme.typography.h4,
                                color = MaterialTheme.colors.secondaryVariant
                            )
                        }
                        else{
                            Text(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .align(Alignment.CenterStart),
                                text = text ?: " ",
                                style = MaterialTheme.typography.h4,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colors.secondary
                            )
                        }
                    }
                }
            }
            if(dropDownItems.size > 1){
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(with(LocalDensity.current){textFieldSize.width.toDp()}),
                    offset = DpOffset(x = 0.dp, y = (-8).dp)
                ) {
                    dropDownItems.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                onDropDownItemSelected(index, text)
                            }
                        ) {
                            Text(text = text, color = MaterialTheme.colors.secondary)
                        }
                    }
                }
            }
        }
    }
}