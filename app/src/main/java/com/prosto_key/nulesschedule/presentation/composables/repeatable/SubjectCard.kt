package com.prosto_key.nulesschedule.presentation.composables.repeatable

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.prosto_key.nulesschedule.R
import com.prosto_key.nulesschedule.domain.model.Subject
import com.prosto_key.nulesschedule.domain.model.Teacher

@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,
    data: Subject,
    isExpanded: Boolean = false,
    onExpandClick: () -> Unit = {},
    onAddTeacherClick: () -> Unit = {},
    onTeacherDeleteClick: (teacher: Teacher) -> Unit = {}
) {
    val density = LocalDensity.current
    var teachersHeight by remember { mutableStateOf(100.dp)}

    val contentHeight by remember {
        derivedStateOf {
            if (data.teachers == null) {
                160.dp
            } else {
                when {
                    data.teachers.isEmpty() -> 160.dp
                    else -> 60.dp + 100.dp
                }
            }
        }
    }

    val cardHeight by animateDpAsState(
        targetValue = if(!isExpanded) 80.dp else 80.dp + contentHeight
    )
    Card(
        modifier = modifier.height(cardHeight),
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        backgroundColor = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val iconRotation by animateFloatAsState(targetValue = if(!isExpanded) 0f else 180f)
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .shadow(elevation = 10.dp)
                .background(MaterialTheme.colors.primary)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onExpandClick
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f),
                    text = data.name.replace("\n", ""),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary,
                    maxLines = if(isExpanded) 2 else 1,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(80.dp),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(onClick = onExpandClick) {
                        Icon(
                            modifier = Modifier
                                .size(20.dp)
                                .graphicsLayer {
                                    rotationX = iconRotation
                                },
                            painter = painterResource(id = R.drawable.ic_expand),
                            contentDescription = "",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.fillMaxWidth()){
                    Row(modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Викладачі",
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.secondary
                        )
                        Button(
                            onClick = onAddTeacherClick,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.onPrimary
                            ),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Text(
                                text = "ДОДАТИ",
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }


                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .onPlaced { coords ->
                            teachersHeight = with(density) { coords.size.height.toDp() }
                            if(data.teachers != null && data.teachers.size > 1){
                                Log.d("puk", teachersHeight.toString())
                            }
                        }

                    ) {
                        if(data.teachers != null && data.teachers.isNotEmpty()){
                            data.teachers.forEachIndexed{ id, teacher ->
                                TeacherCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    data = teacher,
                                    onTeacherDeleteClick = {
                                        onTeacherDeleteClick(teacher)
                                    }
                                )
                                if(id != data.teachers.lastIndex) Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                        else{
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = "Викладачів ще не додано",
                                    style = MaterialTheme.typography.h6,
                                    color = MaterialTheme.colors.secondaryVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}