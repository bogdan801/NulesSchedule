package com.prosto_key.nulesschedule.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetMenu(
    modifier: Modifier = Modifier,
    title: String = "",
    onLeftActionClick: (() -> Unit)? = {},
    onRightActionClick: (() -> Unit)? = {},
    leftIcon: @Composable BoxScope.() -> Unit = {},
    rightIcon: @Composable BoxScope.() -> Unit = {},
    sheetState: BottomSheetState,
    menuItems: @Composable ColumnScope.() -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    Column(modifier = modifier){
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
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                if(onLeftActionClick!=null){
                    IconButton(onClick = { onLeftActionClick() }) {
                        leftIcon()
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = title,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.secondary
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                if(onRightActionClick != null){
                    IconButton(onClick = { onRightActionClick() }) {
                        rightIcon()
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
        ) {
            menuItems()
        }
    }
}