package com.mohyeddin.dialyplaner.presentation.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.Lifecycle
import com.mohyeddin.dialyplaner.R
import com.mohyeddin.dialyplaner.common.util.MyCalendar
import com.mohyeddin.dialyplaner.domain.model.PlannedWork
import com.mohyeddin.dialyplaner.presentation.my_components.MyText
import com.mohyeddin.dialyplaner.presentation.my_components.MyTimePicker
import com.mohyeddin.dialyplaner.presentation.my_components.withPersianNumber
import com.mohyeddin.dialyplaner.presentation.plan.composables.DayListItem
import com.mohyeddin.dialyplaner.presentation.plan.composables.PlanListItem
import com.mohyeddin.dialyplaner.ui.values.*
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

enum class SheetActionState {
    ADD,EDIT,INIT
}

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun PlanScreen(viewModel: PlanScreenViewModel = koinViewModel()) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var dialogState by remember {
        mutableStateOf(false)
    }
    var sheetActionState by remember {
        mutableStateOf(SheetActionState.INIT)
    }
    if (LocalLifecycleOwner.current.lifecycle.currentState == Lifecycle.State.STARTED) viewModel.updateState()
    var weakDays by remember {
        mutableStateOf(emptyList<String>())
    }
    var selectedDay by remember {
        mutableStateOf("")
    }
    var plans by remember {
        mutableStateOf(emptyMap<PlannedWork,Boolean>())
    }
    LaunchedEffect(key1 = viewModel.state,dialogState,scaffoldState.bottomSheetState){
        viewModel.state.collect {
            selectedDay = it.selectedDay
            weakDays = it.weak
            plans =  it.dailyPlans
            if (it.snackBarState) scaffoldState.snackbarHostState.showSnackbar(it.snackBarMsg)
        }
    }
    var selectedPlanTitle by remember {
        mutableStateOf("")
    }
    var selectedPlanStartTime by remember {
        mutableStateOf("")
    }
    var selectedPlanEndTime by remember {
        mutableStateOf("")
    }
    var selectedPlanId by remember {
        mutableStateOf(1L)
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                val collapseIcon = painterResource(id = R.drawable.baseline_expand_more_24)
                val addIcon = painterResource(id = R.drawable.baseline_add_24)

                Column {
                    Button(
                        onClick = {
                            scope.launch {
                                if (scaffoldState.bottomSheetState.isExpanded){
                                    scaffoldState.bottomSheetState.collapse()
                                }
                                else{
                                    scaffoldState.bottomSheetState.expand()
                                    sheetActionState = SheetActionState.ADD
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(BottomSheetScaffoldDefaults.SheetPeekHeight) ,
                        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (scaffoldState.bottomSheetState.isExpanded) MaterialTheme.colors.background else MaterialTheme.colors.primary
                        )
                    ) {
                        Icon(
                            if (scaffoldState.bottomSheetState.isExpanded)  collapseIcon else addIcon,
                            contentDescription = ""
                        )
                    }
                    Spacer(modifier = Modifier.size(5.dp))
                    Column(Modifier.padding(15.dp)) {
                        OutlinedTextField(
                            value = selectedPlanTitle.withPersianNumber() ,
                            onValueChange ={
                                selectedPlanTitle = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                MyText(text = TITLE_INPUT_LABEL)
                            }
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
//                OutlinedTextField(
//                    value = startTime,
//                    onValueChange = {
//                        startTime = it
//                    },
//                    modifier = Modifier.weight(1f),
//                    label = {
//                        Text(text = START_TIME_INPUT_LABEL)
//                    }
//                )
                            MyTimePicker(
                                selectedPlanStartTime,
                                Modifier.weight(1f),
                                START_TIME_INPUT_LABEL
                            ){
                                selectedPlanStartTime=it
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            MyTimePicker(
                                value = selectedPlanEndTime,
                                modifier = Modifier.weight(1f),
                                label = END_TIME_INPUT_LABEL,
                            ){
                                selectedPlanEndTime = it
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Button(
                            onClick = {
                                if (sheetActionState == SheetActionState.ADD) {
                                    viewModel.addPlannedWork(selectedPlanTitle,selectedPlanStartTime,selectedPlanEndTime)
                                }else if (sheetActionState == SheetActionState.EDIT){
                                    viewModel.updatePlannedWork(plannedWork = PlannedWork(id=selectedPlanId,workTitle = selectedPlanTitle, startTime = selectedPlanStartTime, endTime = selectedPlanEndTime))
                                }
                                selectedPlanTitle = ""
                                selectedPlanStartTime = ""
                                selectedPlanEndTime = ""
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            MyText(text = ADD_BUTTON_TITLE, fontSize = 20.sp)
                        }
                    }
                }

            },
            sheetElevation = 20.dp,
            sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            sheetGesturesEnabled = false,
            snackbarHost = {
                SnackbarHost(hostState = it)
            },
            backgroundColor = Color.LightGray
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .blur(if (scaffoldState.bottomSheetState.isCollapsed) 0.dp else 15.dp)
                    .padding(paddingValues)
            ){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .padding(15.dp)) {
                    //rendering day list to switch between days of this weak
                    Row {
                        for (day in weakDays) {
                            val date = MyCalendar(day)
                            DayListItem(
                                Modifier.weight(1f),
                                dayOfWeak = date.dayOfWeekString.first().toString(),
                                dayOfMonth = date.day.toString(),
                                isSelected = day == selectedDay,
                                disabled = scaffoldState.bottomSheetState.isExpanded
                            ) {
                                viewModel.changeSelectedDay(day)
                            }
                            Spacer(modifier = Modifier.size(2.dp))
                        }
                    }
                    Spacer(modifier = Modifier.size(15.dp))
                    LazyColumn {
                        items(plans.toList()){ pair ->
                            PlanListItem(
                                plannedWork = pair.first,
                                isDone = pair.second,
                                disabled = scaffoldState.bottomSheetState.isExpanded,
                                onCheckChange = {
                                    if (it) viewModel.changeWorkStateToDone(pair.first)
                                    else viewModel.changeWorkStateToUnDone(pair.first.id)
                                },
                                onDelete = {
                                    selectedPlanId = pair.first.id
                                    selectedPlanTitle = pair.first.workTitle
                                    selectedPlanStartTime = pair.first.startTime
                                    selectedPlanEndTime = pair.first.endTime
                                    dialogState = true
                                }
                            ) {
                                selectedPlanId = pair.first.id
                                selectedPlanTitle = pair.first.workTitle
                                selectedPlanStartTime = pair.first.startTime
                                selectedPlanEndTime = pair.first.endTime
                                sheetActionState = SheetActionState.EDIT
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        }
                    }
                }
                if (dialogState) AlertDialog(
                    onDismissRequest = { dialogState = false },
                    title = {
                        MyText(text = DELETE_ALERT_DIALOG_TITLE)
                    },
                    properties = DialogProperties(dismissOnClickOutside = true, securePolicy = SecureFlagPolicy.SecureOn),
                    dismissButton = {
                        TextButton(onClick = { dialogState = false }) {
                            MyText(text = NO_BUTTON)
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            dialogState = false
                            viewModel.deletePlannedWork(PlannedWork(selectedPlanId,selectedPlanTitle,selectedPlanStartTime,selectedPlanEndTime))
                            selectedPlanId = 0
                            selectedPlanTitle = ""
                            selectedPlanStartTime = ""
                            selectedPlanEndTime = ""
                        }) {
                            MyText(text = YES_BUTTON)
                        }
                    }
                )
            }
        }
    }

}
