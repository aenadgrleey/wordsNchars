package com.wordsnchars.ui_compose.selectors

import android.graphics.Typeface.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun StyleSelector(styleSet: (Int) -> Unit, navigateUp: () -> Unit) {
    Surface(
        Modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
            //normal 
            ElevatedButton(
                shape = CircleShape,
                onClick = {
                    styleSet(BOLD)
                    navigateUp()
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(4.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp,
                    disabledElevation = 0.dp
                )
            ) { Text(text = "Bold", fontWeight = FontWeight.Bold) }
            ElevatedButton(
                shape = CircleShape,
                onClick = {
                    styleSet(ITALIC)
                    navigateUp()
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(4.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp,
                    disabledElevation = 0.dp
                )
            ) { Text(text = "Italic", fontStyle = FontStyle.Italic) }
            ElevatedButton(
                shape = CircleShape,
                onClick = {
                    styleSet(BOLD_ITALIC)
                    navigateUp()
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(4.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text(
                    text = "Bold&Italic",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}