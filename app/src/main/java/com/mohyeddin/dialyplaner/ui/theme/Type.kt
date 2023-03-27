package com.mohyeddin.dialyplaner.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mohyeddin.dialyplaner.R

val vazir = FontFamily(Font(R.font.vazir))
// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = vazir,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = vazir,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = vazir,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    defaultFontFamily = vazir
)