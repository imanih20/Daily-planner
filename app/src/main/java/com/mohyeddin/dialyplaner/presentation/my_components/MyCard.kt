package com.mohyeddin.dialyplaner.presentation.my_components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyCard(
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colors.surface,
    content: @Composable ()->Unit
){
    Card(
        modifier.clip(RoundedCornerShape(15.dp)),
        elevation = 10.dp,
        shape = RoundedCornerShape(15.dp),
        backgroundColor = background
    ) {
        content()
    }
}