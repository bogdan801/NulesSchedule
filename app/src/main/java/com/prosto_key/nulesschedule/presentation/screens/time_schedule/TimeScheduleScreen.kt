package com.prosto_key.nulesschedule.presentation.screens.time_schedule

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prosto_key.nulesschedule.R
import com.prosto_key.nulesschedule.presentation.composables.BottomSheetMenu
import com.prosto_key.nulesschedule.presentation.composables.TimeScheduleViewer
import com.prosto_key.nulesschedule.presentation.composables.layout.BottomSheetLayout
import com.prosto_key.nulesschedule.presentation.composables.repeatable.MenuItem
import com.prosto_key.nulesschedule.presentation.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TimeScheduleScreen(
    navController: NavHostController,
    viewModel: TimeScheduleViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    if(viewModel.isEditMode.value) {
        BackHandler(enabled = true) {
            viewModel.setEditMode(false)
        }
    }

    BottomSheetLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        sheetContent = { sheetState, expansionFraction ->
            BottomSheetMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                title = if(!viewModel.isEditMode.value) "Графік занять" else "Редагування графіку",
                leftIcon = {
                    Icon(
                        modifier = Modifier
                            .size(22.dp)
                            .graphicsLayer {
                                rotationX = (expansionFraction * 180f) + 180f
                            },
                        painter = painterResource(id = R.drawable.ic_expand),
                        contentDescription = "",
                        tint = MaterialTheme.colors.secondary
                    )
                },
                onLeftActionClick = {
                    scope.launch {
                        if(sheetState.isExpanded) sheetState.collapse()
                        else if(sheetState.isCollapsed) sheetState.expand()
                    }
                },
                rightIcon = {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        imageVector = if(!viewModel.isEditMode.value) Icons.Default.Edit else Icons.Default.Close,
                        contentDescription = "",
                        tint = MaterialTheme.colors.secondary
                    )
                },
                onRightActionClick = {
                    viewModel.setEditMode(!viewModel.isEditMode.value)
                },
                menuItems = {
                    MenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        icon = {
                            Icon(
                                modifier = Modifier.size(26.dp),
                                painter = painterResource(id = R.drawable.ic_home),
                                contentDescription = "",
                                tint = MaterialTheme.colors.secondary
                            )
                        },
                        title = "Головна",
                        onItemClick = {
                            scope.launch{
                                sheetState.collapse()
                                navController.navigate(Screen.ScheduleScreen.withArgs("-1")){
                                    popUpTo(0)
                                }
                            }
                        }
                    )
                    MenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        icon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_time),
                                contentDescription = "",
                                tint = MaterialTheme.colors.secondary
                            )
                        },
                        title = "Графік занять",
                        onItemClick = {
                            scope.launch{
                                sheetState.collapse()
                            }
                        }
                    )
                    MenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        icon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_check),
                                contentDescription = "",
                                tint = MaterialTheme.colors.secondary
                            )
                        },
                        title = "Поточні предмети",
                        onItemClick = {}
                    )
                    MenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        icon = {
                            Icon(
                                modifier = Modifier.size(22.dp),
                                painter = painterResource(id = R.drawable.ic_archive),
                                contentDescription = "",
                                tint = MaterialTheme.colors.secondary
                            )
                        },
                        title = "Архів розкладів",
                        onItemClick = {}
                    )
                }
            )

        }
    ) { _, _ ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 106.dp, start = 8.dp, end = 8.dp),
            contentAlignment = Alignment.Center
        ){
            TimeScheduleViewer(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .heightIn(max = 600.dp),
                data = viewModel.timeSchedule.value
            ){ lessonID, isStart ->
                if(viewModel.isEditMode.value){
                    viewModel.chooseNewTime(lessonID, isStart, context)
                }
            }
        }
    }
}