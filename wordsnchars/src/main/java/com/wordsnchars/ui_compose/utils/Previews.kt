package com.wordsnchars.ui_compose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wordsnchars.ViewModelTextEditor
import com.wordsnchars.ui_compose.TextEditorToolbarDirections
import com.wordsnchars.ui_compose.ToolbarHome
import com.wordsnchars.ui_compose.selectors.ColorSelector
import com.wordsnchars.ui_compose.selectors.ScriptionSelector
import com.wordsnchars.ui_compose.selectors.SizeSelector
import com.wordsnchars.ui_compose.selectors.StyleSelector

@Preview(showBackground = true)
@Composable
fun HomePreview(viewModelTextEditor: ViewModelTextEditor = ViewModelTextEditor()) {
    Box(
        Modifier
            .height(50.dp)
            .width(300.dp)
    ) {
        ToolbarHome(viewModelTextEditor = viewModelTextEditor, directions = object :
            TextEditorToolbarDirections {
            override fun navigateToColorSelector() {}
            override fun navigateToSizeSelector() {}
            override fun navigateToStyleSelector() {}
            override fun navigateToScriptionSelector() {}
            override fun navigateUp() {}
        })
    }
}

@Preview(showBackground = true)
@Composable
fun ColorSelectionPreview() {
    Box(
        Modifier
            .height(50.dp)
            .width(300.dp)) {
        ColorSelector({}) { }
    }
}

@Preview(showBackground = true)
@Composable
fun StyleSelectorPreview() {
    Box(
        Modifier
            .width(300.dp)
            .height(50.dp)
    ) {
        StyleSelector({}) { }
    }
}

@Preview(showBackground = true)
@Composable
fun ScriptionSelectorPreview() {
    Box(
        Modifier
            .height(50.dp)
            .width(300.dp)
    ) {
        ScriptionSelector({}) {}
    }
}

@Preview(showBackground = true)
@Composable
fun SizeSelectorPreview() {
    Box(
        Modifier
            .width(300.dp)
            .height(50.dp)
    ) {
        SizeSelector(remember { mutableStateOf(1f) }, {}) {}
    }
}


@Preview(showBackground = true)
@Composable
fun ToolbarSurfacePreview() {
    Box(
        Modifier
            .background(Color.White)
            .height(100.dp)
            .width(400.dp),
        contentAlignment = Alignment.Center
    ) {
        ToolbarSurface {}
    }
}