package com.wordsnchars.ui_compose.selectors

import android.graphics.Color.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wordsnchars.ui_compose.utils.ColorForPalette


val yellowHighlightPalette = parseColor("#FFC145")
val greenHighlightPalette = parseColor("#00A676")
val purpleHighlightPalette = parseColor("#52528C")
val redHighlightPalette = parseColor("#F4442E")
val blueHighlightPalette = parseColor("#2892D7")

val yellowHighlightActual = parseColor("#ffd480")
val greenHighlightActual = parseColor("#80ffdb")
val purpleHighlightActual = parseColor("#afafd0")
val redHighlightActual = parseColor("#f99386")
val blueHighlightActual = parseColor("#93c8eb")

val yellow = ColorForPalette(yellowHighlightPalette, yellowHighlightActual)
val green = ColorForPalette(greenHighlightPalette, greenHighlightActual)
val purple = ColorForPalette(purpleHighlightPalette, purpleHighlightActual)
val red = ColorForPalette(redHighlightPalette, redHighlightActual)
val blue = ColorForPalette(blueHighlightPalette, blueHighlightActual)
@Composable
fun ColorSelector(colorSet: (Int) -> Unit, navigateUp: () -> Unit) {
    Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
        ColorButton(
            colorOnPalette = yellowHighlightPalette,
            colorActual = yellowHighlightActual,
            modifier = Modifier.weight(1f),
            colorSet = colorSet,
            navigateUp = navigateUp
        )
        ColorButton(
            colorOnPalette = greenHighlightPalette,
            colorActual = greenHighlightActual,
            modifier = Modifier.weight(1f),
            colorSet = colorSet,
            navigateUp = navigateUp
        )
        ColorButton(
            colorOnPalette = purpleHighlightPalette,
            colorActual = purpleHighlightActual,
            modifier = Modifier.weight(1f),
            colorSet = colorSet,
            navigateUp = navigateUp
        )
        ColorButton(
            colorOnPalette = redHighlightPalette,
            colorActual = redHighlightActual,
            modifier = Modifier.weight(1f),
            colorSet = colorSet,
            navigateUp = navigateUp
        )
        ColorButton(
            colorOnPalette = blueHighlightPalette,
            colorActual = blueHighlightActual,
            modifier = Modifier.weight(1f),
            colorSet = colorSet,
            navigateUp = navigateUp
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorButton(
    colorOnPalette: Int,
    colorActual: Int,
    modifier: Modifier,
    colorSet: (Int) -> Unit,
    navigateUp: () -> Unit
) {
    OutlinedCard(
        shape = CircleShape,
        onClick = {
            colorSet(colorActual)
            navigateUp()
        },
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp),
        border = BorderStroke(
            color = MaterialTheme.colorScheme.surfaceVariant,
            width = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(colorOnPalette),
        )
    ) {}
}