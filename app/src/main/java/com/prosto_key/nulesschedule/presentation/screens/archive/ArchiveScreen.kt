package com.prosto_key.nulesschedule.presentation.screens.archive

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
                    scope.launch {
                        sheetState.collapse()
                        navController.navigate(Screen.ScheduleScreen.withArgs("-2")){
                            popUpTo(0)
                        }
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
                        title = "Розклад дзвінків",
                        onItemClick = {
                            scope.launch{
                                sheetState.collapse()
                                navController.navigate(Screen.TimeScheduleScreen.route)
                            }
                        }
                    )
                    if(viewModel.schedulesList.value.isNotEmpty()){
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
                }
            )
        }
    ) { _, _, snackBarState ->
        if(viewModel.schedulesList.value.isNotEmpty()){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 106.dp, start = 8.dp, end = 8.dp),
            ){
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                itemsIndexed(viewModel.schedulesList.value){ id, schedule ->
                    ScheduleCard(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .height(160.dp),
                        data = schedule,
                        isSelected = schedule.scheduleID == viewModel.selectedSchedule.value,
                        onCardClick = {
                            scope.launch {
                                viewModel.selectSchedule(schedule.scheduleID)
                                navController.navigate(Screen.ScheduleScreen.withArgs(schedule.scheduleID.toString())){
                                    popUpTo(0)
                                }
                            }
                        },
                        onScheduleDeleteClick = {
                            scope.launch {
                                snackBarState.currentSnackbarData?.dismiss()
                                val result = snackBarState.showSnackbar("Ви дійсно бажаєте видалити даний розклад?", "ТАК", SnackbarDuration.Short)
                                if(result == SnackbarResult.ActionPerformed){
                                    viewModel.deleteSchedule(schedule, id)
                                }
                            }
                        }
                    )
                }
            }
        }
        else{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 106.dp, start = 40.dp, end = 40.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Розкладу ще не додано.\nНатисніть на \"+\" та відкрийте файл розкладу",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}