package com.prosto_key.nulesschedule.presentation.screens.archive

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prosto_key.nulesschedule.R
import com.prosto_key.nulesschedule.presentation.composables.BottomSheetMenu
import com.prosto_key.nulesschedule.presentation.composables.layout.BottomSheetLayout
import com.prosto_key.nulesschedule.presentation.composables.repeatable.MenuItem
import com.prosto_key.nulesschedule.presentation.composables.repeatable.ScheduleCard
import com.prosto_key.nulesschedule.presentation.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArchiveScreen(
    navController: NavHostController,
    viewModel: ArchiveViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    BottomSheetLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        sheetContent = { sheetState, expansionFraction ->
            BottomSheetMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(520.dp),
                title = "Архів розкладів",
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
                        modifier = Modifier.size(34.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = MaterialTheme.colors.secondary
                    )
                },
                onRightActionClick = {
                    TODO()
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
                                scope.launch{
                                    sheetState.collapse()
                                    navController.navigate(Screen.ScheduleScreen.withArgs("-1")){
                                        popUpTo(0)
                                    }
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
                        title = "Розклад дзвінків",
                        onItemClick = {
                            scope.launch{
                                sheetState.collapse()
                                navController.navigate(Screen.TimeScheduleScreen.route)
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
                        title = "Дисципліни групи",
                        onItemClick = {
                            scope.launch {
                                sheetState.collapse()
                                navController.navigate(Screen.SubjectsScreen.withArgs("-1", "-1"))
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
                        title = "Архів розкладів",
                        onItemClick = {
                            scope.launch {
                                sheetState.collapse()
                            }
                        }
                    )
                }
            )
        }
    ) { sheetState, _, snackBarState ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 106.dp, start = 8.dp, end = 8.dp),
        ){
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(viewModel.schedulesList.value){ schedule ->
                ScheduleCard(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .height(160.dp),
                    data = schedule,
                    onCardClick = {
                        viewModel.selectSchedule(schedule.scheduleID)
                        navController.navigate(Screen.ScheduleScreen.withArgs(schedule.scheduleID.toString())){
                            popUpTo(0)
                        }
                    },
                    onScheduleDeleteClick = {

                    }
                )
            }
        }
    }
}