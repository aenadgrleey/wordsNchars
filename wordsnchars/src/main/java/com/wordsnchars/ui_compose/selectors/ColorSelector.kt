package com.wordsnchars.ui_compose.selectors

import android.graphics.Color.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val yellowHighlight = parseColor("#FFC145")
val greenHighlight = parseColor("#00A676")
val purpleHighlight = parseColor("#52528C")
val redHighlight = parseColor("#F4442E")
val blueHighlight = parseColor("#2892D7")

@Composable
fun ColorSelector(colorSet: (Int) -> Unit, navigateUp: () -> Unit) {
    Surface(
        Modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
            ColorButton(color = yellowHighlight, modifier = Modifier.weight(1f), colorSet = colorSet, navigateUp = navigateUp)
            ColorButton(color = greenHighlight, modifier = Modifier.weight(1f), colorSet = colorSet, navigateUp = navigateUp)
            ColorButton(color = purpleHighlight, modifier = Modifier.weight(1f), colorSet = colorSet, navigateUp = navigateUp)
            ColorButton(color = redHighlight, modifier = Modifier.weight(1f), colorSet = colorSet, navigateUp = navigateUp)
            ColorButton(color = blueHighlight, modifier = Modifier.weight(1f), colorSet = colorSet, navigateUp = navigateUp)

        }
    }
}

@Composable
fun ColorButton(color: Int, modifier: Modifier, colorSet: (Int) -> Unit, navigateUp: () -> Unit) {
    ElevatedButton(
        shape = CircleShape,
        onClick = {
            colorSet(color)
            navigateUp()
        },
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp,
            disabledElevation = 0.dp
        ),
        border = BorderStroke(
            color = MaterialTheme.colorScheme.background,
            width = 4.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(color),
        )
    ) {}
}