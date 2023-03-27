package com.mohyeddin.dialyplaner.presentation.plan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.dialyplaner.domain.model.DoneWork
import com.mohyeddin.dialyplaner.domain.model.PlannedWork
import com.mohyeddin.dialyplaner.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PlanScreenViewModel(private val repository: Repository) : ViewModel() {
    private var _state = MutableStateFlow(PlanScreenUiState())
    val state get() = _state
    init {
        updateState()
    }

    fun updateState(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(
                PlanScreenUiState(
                    selectedDay = repository.getToday(),
                    weak = repository.calculateWeak(),
                    dailyPlans = repository.getDailyPlan()
                )
            )
        }
    }

    fun changeSelectedDay(day: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(
                state.value.copy(selectedDay = day, dailyPlans = repository.getDailyPlan(day))
            )
        }
    }

    fun addPlannedWork(title: String,startTime: String, endTime: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (validatePlannedWork(title,startTime,endTime)) return@launch
            repository.insertWork(title,startTime,endTime)
            updatePlanList()
        }
    }

    private suspend fun validatePlannedWork(title: String, startTime: String, endTime: String) : Boolean{
        var st = false
        if (title.isEmpty() || startTime.isEmpty() || endTime.isEmpty()){
           st = true
            _state.emit(
                state.value.copy(snackBarMsg = "please fill all of fields", snackBarState = true)
            )
        }
        delay(1000)
        _state.emit(
            state.value.copy( snackBarState = false)
        )
        return st
    }
    private suspend fun updatePlanList(){
        _state.emit(
            state.value.copy(dailyPlans = repository.getDailyPlan(state.value.selectedDay))
        )
    }
    fun updatePlannedWork(plannedWork: PlannedWork){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWork(plannedWork)
            updatePlanList()
        }
    }

    fun changeWorkStateToDone(plannedWork: PlannedWork){
        viewModelScope.launch(Dispatchers.IO) {
            repository.doWork(doneWork = DoneWork(pid = plannedWork.id, date = state.value.selectedDay))
            updatePlanList()
        }
    }

    fun changeWorkStateToUnDone(pid: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.unDoWork(pid, state.value.selectedDay)
            updatePlanList()
        }
    }

    fun deletePlannedWork(plannedWork: PlannedWork) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWork(plannedWork)
            updatePlanList()
        }
    }
}

data class PlanScreenUiState(
    var selectedDay: String = "",
    var dailyPlans: Map<PlannedWork,Boolean> = emptyMap(),
    var weak: List<String> = emptyList(),
    var snackBarMsg : String = "",
    var snackBarState: Boolean = false
)