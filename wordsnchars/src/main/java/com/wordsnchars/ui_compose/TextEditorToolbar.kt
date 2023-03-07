package com.wordsnchars.ui_compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wordsnchars.ViewModelTextEditor
import com.wordsnchars.ui_compose.selectors.ColorSelector
import com.wordsnchars.ui_compose.selectors.ScriptionSelector
import com.wordsnchars.ui_compose.selectors.SizeSelector
import com.wordsnchars.ui_compose.selectors.StyleSelector
import com.wordsnchars.ui_compose.ui.theme.WordsNcharsTheme
import com.wordsnchars.ui_compose.utils.ToolbarSurface

@ExperimentalFoundationApi
@Preview
@Composable
fun Toolbar(viewModelTextEditor: ViewModelTextEditor = ViewModelTextEditor()) {
    val navController = rememberNavController()
    val directions = object : TextEditorToolbarDirections {
        override fun navigateToColorSelector() {
            navController.navigate("color_selector")
        }

        override fun navigateToSizeSelector() {
            navController.navigate("size_selector")
        }

        override fun navigateToStyleSelector() {
            navController.navigate("style_selector")
        }

        override fun navigateToScriptionSelector() {
            navController.navigate("scription_selector")
        }

        override fun navigateUp() {
            navController.navigateUp()
        }
    }
    WordsNcharsTheme {
        ToolbarSurface {

            NavHost(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    ToolbarHome(viewModelTextEditor = viewModelTextEditor, directions = directions)
                }
                composable("style_selector") {
                    StyleSelector(viewModelTextEditor::styleChange, directions::navigateUp)
                }
                composable("size_selector") {
                    SizeSelector(
                        current = viewModelTextEditor.fontSizeMultiplier.collectAsState(),
                        sizeSet = viewModelTextEditor::sizeChange,
                        navigateUp = directions::navigateUp
                    )
                }
                composable("color_selector") {
                    ColorSelector(viewModelTextEditor::highlightColorChange, directions::navigateUp)
                }
                composable("scription_selector") {
                    ScriptionSelector(viewModelTextEditor::scriptionChange, directions::navigateUp)
                }
            }
        }
    }
}

interface TextEditorToolbarDirections {
    fun navigateToStyleSelector()
    fun navigateToColorSelector()
    fun navigateToSizeSelector()
    fun navigateToScriptionSelector()
    fun navigateUp()
}


