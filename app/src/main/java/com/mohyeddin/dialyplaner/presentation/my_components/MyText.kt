package com.mohyeddin.dialyplaner.presentation.my_components

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.mohyeddin.dialyplaner.R

internal fun String.withPersianNumber() : String {
    var newString = toString()
    for (c in toCharArray()) {
        if (c.isDigit()){
            newString = when(c){
                '0'-> newString.replace(c,'۰')
                '1'-> newString.replace(c,'۱')
                '2'-> newString.replace(c,'۲')
                '3'-> newString.replace(c,'۳')
                '4'-> newString.replace(c,'۴')
                '5'-> newString.replace(c,'۵')
                '6'-> newString.replace(c,'۶')
                '7'-> newString.replace(c,'۷')
                '8'-> newString.replace(c,'۸')
                '9'-> newString.replace(c,'۹')
                else -> newString
            }
        }
    }
    return newString
}

@Composable
fun MyText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
){
    Text(
        text.withPersianNumber(),
        modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily = FontFamily(
            Font(R.font.vazir)
        ),
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        softWrap,
        maxLines,
        minLines,
        onTextLayout,
        style
    )
}