package com.mohyeddin.dialyplaner.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.dialyplaner.common.util.MyCalendar
import com.mohyeddin.dialyplaner.domain.model.PlannedWork
import com.mohyeddin.dialyplaner.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private var _state = MutableStateFlow(HomeUiState())
    val state get() = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val todayScore = if (repository.getDayScore().isNaN()) 0f else repository.getDayScore()
            val yesterdayScore = if (repository.getDayScore(MyCalendar().yesterday.toString()).isNaN()) 0f else repository.getDayScore(MyCalendar().yesterday.toString())
            val weakScore = if (repository.getWeakScore().isNaN()) 0f else repository.getWeakScore()
            _state.emit(
                HomeUiState(
                    yesterdayScore,
                    todayScore,
                    weakScore,
                    repository.getAllPlannedWork()
                )
            )
        }
    }
}
data class HomeUiState(
    var yesterdayScore: Float = 0f,
    var todayScore: Float = 0f,
    var weakScore: Float = 0f,
    var taskList : List<PlannedWork> = emptyList()
)