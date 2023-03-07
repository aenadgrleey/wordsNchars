package com.wordsnchars.ui_compose.selectors

import android.graphics.Typeface.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.wordsnchars.ui_compose.utils.ToolbarButton
import com.wordsnchars.ui_compose.utils.ToolbarSurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyleSelector(styleSet: (Int) -> Unit, navigateUp: () -> Unit) {

    Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
        //normal
        ToolbarButton(
            onClick = {
                styleSet(BOLD)
                navigateUp()
            },
            onLongClick = {
                styleSet(BOLD)
                navigateUp()
            },
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            enabled = remember { mutableStateOf(false) }
        ) {
            Text(text = "Bold", fontWeight = FontWeight.Bold)

        }
        ToolbarButton(
            onClick = {
                styleSet(ITALIC)
                navigateUp()
            },
            onLongClick = {
                styleSet(ITALIC)
                navigateUp()
            },
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            enabled = remember { mutableStateOf(false) }
        ) { Text(text = "Italic", fontStyle = FontStyle.Italic) }
        ToolbarButton(
            onClick = {
                styleSet(BOLD_ITALIC)
                navigateUp()
            },
            onLongClick = {
                styleSet(BOLD_ITALIC)
                navigateUp()
            },
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(4.dp),
            enabled = remember { mutableStateOf(false) }
        ) {
            Text(
                text = "Bold&Italic",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
