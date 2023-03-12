package com.prosto_key.nulesschedule.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    data: List<String> = listOf(),
    index: Int = 0,
    title: String = "",
    showIcon: Boolean = true,
    showFullTextInItems: Boolean = true,
    onItemSelected: (index: Int, text: String) -> Unit = { _: Int, _: String -> }
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        if(title.isNotBlank()){
            Text(
                modifier = Modifier.padding(vertical = 2.dp),
                text = title,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.secondary
            )
        }
        Box(
            modifier = modifier
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colors.onPrimary)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.secondary,
                    shape = MaterialTheme.shapes.small
                )
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable {
                    if (data.isNotEmpty()) expanded = !expanded
                }
        ){
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp, end = if (showIcon) 25.dp else 8.dp),
                text = if(data.isEmpty()) "" else data[index],
                style = MaterialTheme.typography.h4,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.secondary
            )
            if(data.isNotEmpty() && showIcon){
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp),
                    imageVector = icon,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = "Icon"
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current){textFieldSize.width.toDp()}),
            offset = DpOffset(x = 0.dp, y = (-8).dp)
        ) {
            data.forEachIndexed { index, text ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onItemSelected(index, text)
                    }
                ) {
                    if(showFullTextInItems){
                        Text(text = text, color = MaterialTheme.colors.secondary)
                    }
                    else{
                        Text(text = text, color = MaterialTheme.colors.secondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
}