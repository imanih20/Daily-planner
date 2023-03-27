package com.mohyeddin.dialyplaner.presentation.plan.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohyeddin.dialyplaner.domain.model.PlannedWork
import com.mohyeddin.dialyplaner.presentation.my_components.MyCard
import com.mohyeddin.dialyplaner.presentation.my_components.MyText
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState

@Composable
fun PlanListItem(plannedWork: PlannedWork, isDone: Boolean, modifier: Modifier = Modifier, disabled: Boolean = false, onCheckChange:(Boolean)->Unit, onDelete:()->Unit, onEdit:()->Unit){

    val deleteAction = SwipeAction(
        {
            onDelete()
        },
        rememberVectorPainter(image = Icons.Default.Delete),
        Color.Red,
    )
    val editAction = SwipeAction(
        {
            onEdit()
        },
        rememberVectorPainter(image = Icons.Default.Edit),
        Color.Green,
        weight = 1.0,
    )
    val swipeState = rememberSwipeableActionsState()
    val content = @Composable {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            MyCard(
                modifier
                    .padding(2.dp)
                    .fillMaxWidth()) {
                Row(Modifier.padding(10.dp),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        MyText(text = plannedWork.workTitle, fontSize = 22.sp)
                        Spacer(modifier = Modifier.size(5.dp))
                        MyText(text = "${plannedWork.startTime}-${plannedWork.endTime}")
                    }
                    Box(modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colors.onBackground, shape = CircleShape)
                        .clickable(enabled = !disabled) {
                            onCheckChange(!isDone)
                        }){
                        if (isDone) Icon( Icons.Filled.CheckCircle, modifier = Modifier.matchParentSize(), contentDescription = "", tint = Color.Green)
                    }
                }
            }
        }
    }
    if (disabled)
        content()
    else
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            SwipeableActionsBox(
                startActions = listOf(editAction),
                endActions = listOf(deleteAction),
                swipeThreshold = 25.dp,
                state = swipeState,
                backgroundUntilSwipeThreshold = MaterialTheme.colors.secondary
            ) {
                content()
            }
        }


}
