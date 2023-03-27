package com.mohyeddin.dialyplaner.presentation.my_components

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import java.util.*

@Composable
fun MyTimePicker(
    value: String,
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (String)->Unit
){
    val time = Calendar.getInstance()
    val dialog = TimePickerDialog(
        LocalContext.current,
        { _, hourOfDay, minute ->
            val t = "$hourOfDay:$minute"
            onValueChange(t)
        },
        time.get(Calendar.HOUR_OF_DAY),
        time.get(Calendar.MINUTE),
        true
    )
    OutlinedTextField(
        value = value.withPersianNumber(),
        onValueChange = onValueChange,
        modifier = modifier,
        leadingIcon = {
            Icon(painterResource(id = com.mohyeddin.dialyplaner.R.drawable.baseline_access_time_24), contentDescription = "", modifier = Modifier.clickable { dialog.show() })
        },
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = "${time.get(Calendar.HOUR_OF_DAY)}:${time[Calendar.MINUTE]}")
        }
    )
}