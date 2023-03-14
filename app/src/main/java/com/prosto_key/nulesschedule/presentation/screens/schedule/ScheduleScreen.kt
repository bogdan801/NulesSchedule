package com.prosto_key.nulesschedule.presentation.screens.schedule

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prosto_key.nulesschedule.R
import com.prosto_key.nulesschedule.presentation.composables.AddScheduleSheet
import com.prosto_key.nulesschedule.presentation.composables.BottomSheetMenu
import com.prosto_key.nulesschedule.presentation.composables.ScheduleViewer
import com.prosto_key.nulesschedule.presentation.composables.layout.BottomSheetLayout
import com.prosto_key.nulesschedule.presentation.composables.repeatable.MenuItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun ScheduleScreen(
    navController: NavHostController,
    viewModel: ScheduleViewModel = hiltViewModel(),
    launcher: ActivityResultLauncher<Intent>
) {
    val scope = rememberCoroutineScope()
    var showAddSheet by rememberSaveable {
        mutableStateOf(false)
    }

    BottomSheetLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        sheetContent = { sheetState, expansionFraction ->
            LaunchedEffect(key1 = sheetState.isCollapsed){
                if(sheetState.isCollapsed) {
                    showAddSheet = false
                }
            }
            AnimatedContent(
                targetState = showAddSheet,
                transitionSpec = {
                    fadeIn() with fadeOut()
                }
            ) { showAdd ->
                if(!showAdd){
                    BottomSheetMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp),
                        title = "Розклад НУБіП",
                        leftIcon = {
                            Icon(
                                modifier = Modifier
                                    .size(22.dp)
                                    .graphicsLayer {
                                        rotationX = expansionFraction * 180f
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
                                modifier = Modifier.size(34.dp),
                                imageVector = Icons.Default.Add,
                                contentDescription = "",
                                tint = MaterialTheme.colors.secondary
                            )
                        },
                        onRightActionClick = {
                            showAddSheet = true
                            scope.launch {
                                delay(400)
                                if(sheetState.isCollapsed) sheetState.expand()
                            }
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
                                onItemClick = {}
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
                else{
                    AddScheduleSheet(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp),
                        openedFileName = viewModel.fileName,
                        onOpenFileClick = {
                            viewModel.openFile(launcher)
                        },
                        majors = viewModel.majors.value,
                        selectedMajor = viewModel.selectedMajor.value,
                        onMajorSelected = { id, _ ->
                            viewModel.selectMajor(id)
                        },
                        years = viewModel.years.value,
                        selectedYear = viewModel.selectedYear.value,
                        onYearSelected = { id, _ ->
                            viewModel.selectYear(id)
                        },
                        groups = viewModel.groups.value,
                        selectedGroup = viewModel.selectedGroup.value,
                        onGroupSelected = { id, _ ->
                            viewModel.selectGroup(id)
                        },
                        onSelectScheduleClick = {
                            viewModel.selectSchedule()
                            scope.launch{
                                sheetState.collapse()
                            }
                        }
                    )
                }
            }
        }
    ) { _, _ ->
        ScheduleViewer(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 106.dp, start = 8.dp, end = 8.dp)
                .fillMaxSize(),
            data = viewModel.week,
            timeSchedule = viewModel.timeSchedule,
            currentDayTime = viewModel.currentDateTime.value
        )
    }
}