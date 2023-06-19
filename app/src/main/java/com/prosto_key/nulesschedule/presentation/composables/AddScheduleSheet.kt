package com.prosto_key.nulesschedule.presentation.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prosto_key.nulesschedule.R

@Composable
fun AddScheduleSheet(
    modifier: Modifier = Modifier,
    openedFileName: String = "",
    onOpenFileClick: () -> Unit = {},
    majors: List<String> = listOf(),
    selectedMajor: Int = 0,
    onMajorSelected: (index: Int, text: String) -> Unit = { _: Int, _: String -> },
    years: List<String> = listOf(),
    selectedYear: Int = 0,
    onYearSelected: (index: Int, text: String) -> Unit = { _: Int, _: String -> },
    groups: List<String> = listOf(),
    selectedGroup: Int = 0,
    onGroupSelected: (index: Int, text: String) -> Unit = { _: Int, _: String -> },
    onSelectScheduleClick: () -> Unit = {},
    isLoading: Boolean = false
) {
    val context = LocalContext.current
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Box(modifier = Modifier
            .padding(8.dp)
            .size(60.dp, 6.dp)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.onSurface)
            .align(Alignment.CenterHorizontally)
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ReadOnlyTextCard(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            if (openedFileName.isNotBlank()) Toast
                                .makeText(context, openedFileName, Toast.LENGTH_SHORT)
                                .show()
                        }
                    ),
                text = openedFileName,
                placeholder = stringResource(id = R.string.schedule_file)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier.height(50.dp),
                onClick = onOpenFileClick,
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.open),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        Spacer(modifier = Modifier.height(0.dp))
        CustomDropDownMenu(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
                .height(50.dp),
            title = stringResource(id = R.string.major),
            data = majors,
            index = selectedMajor,
            onItemSelected = onMajorSelected,
            showIcon = false,
        )
        CustomDropDownMenu(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
                .height(50.dp),
            title = stringResource(id = R.string.year),
            data = years,
            index = selectedYear,
            onItemSelected = onYearSelected,
            showIcon = false,
        )
        CustomDropDownMenu(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
                .height(50.dp),
            title = stringResource(id = R.string.group),
            data = groups,
            index = selectedGroup,
            onItemSelected = onGroupSelected,
            showIcon = false,
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Button(
                modifier = Modifier.size(220.dp, 55.dp),
                onClick = onSelectScheduleClick,
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    disabledBackgroundColor = MaterialTheme.colors.secondary
                ),
                enabled = !isLoading
            ) {
                if(!isLoading){
                    Text(
                        text = stringResource(id = R.string.select_schedule),
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
                else{
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp)
                            .offset(y = (-1).dp),
                        color = MaterialTheme.colors.surface,
                        strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}