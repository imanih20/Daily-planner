package com.mohyeddin.dialyplaner.presentation.plan.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohyeddin.dialyplaner.presentation.my_components.MyCard
import com.mohyeddin.dialyplaner.presentation.my_components.MyText


@Composable
fun DayListItem(
    modifier: Modifier = Modifier,
    dayOfWeak: String,
    dayOfMonth: String,
    isSelected: Boolean = false,
    disabled: Boolean = false,
    onclick: () -> Unit,
) {
    val color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface

    MyCard(
        modifier
            .clickable(enabled = !disabled) { onclick() }, background = color) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
            MyText(text = dayOfWeak)
            Spacer(modifier = Modifier.size(15.dp))
            MyText(text = dayOfMonth, fontSize = 22.sp)
        }
    }
}
