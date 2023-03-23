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
fun ReadOnlyTextCard(
    modifier: Modifier = Modifier,
    text: String = "",
    placeholder: String = ""
) {
    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface,
        border = BorderStroke(1.dp , MaterialTheme.colors.secondary),
        shape = MaterialTheme.shapes.small
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp),
            contentAlignment = Alignment.CenterStart
        ){
            if(text.isNotBlank()){
                BasicTextField(
                    value = text,
                    onValueChange = {},
                    maxLines = 1,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.secondary),
                )
            }
            else {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }
}