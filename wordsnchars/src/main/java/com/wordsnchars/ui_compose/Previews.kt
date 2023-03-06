package com.wordsnchars.ui_compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.wordsnchars.ViewModelTextEditor
import com.wordsnchars.ui_compose.selectors.ColorSelector
import com.wordsnchars.ui_compose.selectors.ScriptionSelector
import com.wordsnchars.ui_compose.selectors.SizeSelector
import com.wordsnchars.ui_compose.selectors.StyleSelector

@Preview(showBackground = true)
@Composable
fun HomePreview(viewModelTextEditor: ViewModelTextEditor = ViewModelTextEditor()) {
    ToolbarHome(viewModelTextEditor = viewModelTextEditor, directions = object :
        TextEditorToolbarDirections {
        override fun navigateToColorSelector() {}
        override fun navigateToSizeSelector() {}
        override fun navigateToStyleSelector() {}
        override fun navigateToScriptionSelector() {}
        override fun navigateUp() {}
    })
}

@Preview(showBackground = true)
@Composable
fun ColorSelectionPreview() {
    ColorSelector({}) { }
}

@Preview
@Composable
fun StyleSelectorPreview() {
    StyleSelector({}) { }
}

@Preview
@Composable
fun ScriptionSelectorPreview() {
    ScriptionSelector({}) {}
}

@Preview
@Composable
fun SizeSelectorPreview() {
    SizeSelector(remember { mutableStateOf(1f) }, {}) {}
}