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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prosto_key.nulesschedule.R
import com.prosto_key.nulesschedule.data.datastore.readIntFromDataStore
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
                    .height(520.dp),
                title = if(!viewModel.isEditMode.value) stringResource(id = R.string.time_schedule) else stringResource(id = R.string.edit_schedule),
                titleFontSize = 21.sp,
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
                        title = stringResource(id = R.string.home),
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
                        title = stringResource(id = R.string.time_schedule),
                        onItemClick = {
                            scope.launch{
                                sheetState.collapse()
                            }
                        }
                    )
                    if(viewModel.areSchedulesOpen.value){
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
                            title = stringResource(id = R.string.subjects),
                            onItemClick = {
                                scope.launch {
                                    sheetState.collapse()
                                    val currentScheduleId = (context.readIntFromDataStore("openedScheduleID") ?: -1).toString()
                                    navController.navigate(Screen.SubjectsScreen.withArgs(currentScheduleId, "-1"))
                                }
                            }
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
                            title = stringResource(id = R.string.archive),
                            onItemClick = {
                                scope.launch{
                                    sheetState.collapse()
                                    navController.navigate(Screen.ArchiveScreen.route)
                                }
                            }
                        )
                    }
                }
            )

        }
    ) { _, _, _ ->
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