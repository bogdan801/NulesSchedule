package com.prosto_key.nulesschedule.presentation.composables.layout

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    modifier: Modifier = Modifier,
    sheetContent: @Composable (sheetState: BottomSheetState, expansionFraction: Float) -> Unit = { _: BottomSheetState, _: Float -> },
    backgroundColor: Color = MaterialTheme.colors.surface,
    sheetPeekHeight: Dp = 90.dp,
    sheetElevation: Dp = 20.dp,
    sheetShape: Shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    scrimAlpha: Float = 0.2f,
    content: @Composable (sheetState: BottomSheetState, expansionFraction: Float) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState, drawerState = drawerState)

    val expansionFraction = when{
        sheetState.progress.from == BottomSheetValue.Collapsed && sheetState.progress.to == BottomSheetValue.Expanded -> sheetState.progress.fraction
        sheetState.progress.from == BottomSheetValue.Expanded && sheetState.progress.to == BottomSheetValue.Expanded -> sheetState.progress.fraction
        sheetState.progress.from == BottomSheetValue.Expanded && sheetState.progress.to == BottomSheetValue.Collapsed -> 1f - sheetState.progress.fraction
        else -> 0f
    }

    if(sheetState.isExpanded) {
        BackHandler(enabled = true) {
            scope.launch {
                sheetState.collapse()
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            sheetContent(sheetState, expansionFraction)
        },
        backgroundColor = backgroundColor,
        sheetPeekHeight = sheetPeekHeight,
        sheetElevation = sheetElevation,
        sheetShape = sheetShape
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ){
            content(sheetState, expansionFraction)
            if(expansionFraction != 0f){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                scope.launch {
                                    sheetState.collapse()
                                }
                            }
                        )
                        .background(Color.Black.copy(alpha = expansionFraction * scrimAlpha))
                )
            }
        }
    }
}