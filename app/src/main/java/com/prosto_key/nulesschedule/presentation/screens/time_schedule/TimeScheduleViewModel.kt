package com.prosto_key.nulesschedule.presentation.screens.time_schedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.prosto_key.nulesschedule.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimeScheduleViewModel
@Inject
constructor(
    private val repository: Repository,
    application: Application
): AndroidViewModel(application) {
    //CONTEXT
    private val context get() = getApplication<Application>()


}