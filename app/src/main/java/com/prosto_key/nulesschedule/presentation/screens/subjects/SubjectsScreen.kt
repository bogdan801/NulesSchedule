package com.prosto_key.nulesschedule.presentation.screens.subjects

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prosto_key.nulesschedule.R
import com.prosto_key.nulesschedule.domain.model.Subject
import com.prosto_key.nulesschedule.domain.model.Teacher
import com.prosto_key.nulesschedule.presentation.composables.AddScheduleSheet
import com.prosto_key.nulesschedule.presentation.composables.AddTeacherSheet
import com.prosto_key.nulesschedule.presentation.composables.BottomSheetMenu
import com.prosto_key.nulesschedule.presentation.composables.layout.BottomSheetLayout
import com.prosto_key.nulesschedule.presentation.composables.repeatable.MenuItem
import com.prosto_key.nulesschedule.presentation.composables.repeatable.SubjectCard
import com.prosto_key.nulesschedule.presentation.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun SubjectsScreen(
    navController: NavHostController,
    viewModel: SubjectsViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var showAddSheet by rememberSaveable {
        mutableStateOf(false)
    }

    var currentSubjectID by rememberSaveable { mutableStateOf(-1) }

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
                            .height(520.dp),
                        title = "Поточні предмети",
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
                                        navController.navigate(Screen.SubjectsScreen.route)
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
                    AddTeacherSheet(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(520.dp),
                        teachers = viewModel.allTeachers.value,
                        onAddTeacherClick = { teacher, isNew ->
                            viewModel.addTeacher(teacher, isNew, currentSubjectID)
                            scope.launch {
                                sheetState.collapse()
                            }
                            Toast.makeText(context, "Викладача додано", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    ){ sheetState, _ ->
        val lazyColumnState = rememberLazyListState()
        LaunchedEffect(key1 = viewModel.preOpenedIndex){
            lazyColumnState.scrollToItem(viewModel.preOpenedIndex)
        }

        LazyColumn(
            modifier = Modifier
                .padding(bottom = 80.dp)
                .fillMaxSize(),
            state = lazyColumnState,
            contentPadding = PaddingValues(bottom = 20.dp)
        ){
            items(viewModel.subjects.value){subject ->
                var isExpanded by rememberSaveable { mutableStateOf(viewModel.preOpenedSubjectID.value == subject.subjectID) }
                SubjectCard(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                        .fillMaxWidth(),
                    data = subject,
                    isExpanded = isExpanded,
                    onExpandClick = {
                        isExpanded = !isExpanded
                    },
                    onTeacherDeleteClick = { subjectID, teacherID ->
                        viewModel.deleteTeacherFromSubject(subjectID, teacherID)
                    },
                    onAddTeacherClick = {
                        showAddSheet = true
                        currentSubjectID = subject.subjectID
                        scope.launch {
                            delay(400)
                            if(sheetState.isCollapsed) sheetState.expand()
                        }
                    }
                )
            }
        }
    }
}