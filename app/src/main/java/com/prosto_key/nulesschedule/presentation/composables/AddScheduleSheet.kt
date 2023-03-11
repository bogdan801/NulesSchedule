package com.prosto_key.nulesschedule.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddScheduleSheet(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        FileNameBox(
            modifier = Modifier
                .padding(16.dp)
                .size(250.dp, 50.dp),
            text = "facultet_informaciynih_tehnologiy_10.02.23"
        )
    }
}