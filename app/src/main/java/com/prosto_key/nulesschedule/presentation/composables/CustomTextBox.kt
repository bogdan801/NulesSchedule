package com.prosto_key.nulesschedule.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextBox(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: ((newText: String) -> Unit)? = null,
    isReadOnly: Boolean = onTextChange == null,
    placeholder: String = ""
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colors.onPrimary,
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            if(text.isNotBlank()){
                BasicTextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterStart),
                    value = text,
                    onValueChange = onTextChange ?: {},
                    readOnly = isReadOnly,
                    textStyle = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.secondary),
                    maxLines = 1,
                    singleLine = true
                )
            }
            else{
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterStart),
                    text = placeholder,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.secondaryVariant
                )
            }

        }
    }
}