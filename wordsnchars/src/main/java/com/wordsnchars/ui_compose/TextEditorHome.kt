package com.wordsnchars.ui_compose

import android.graphics.Typeface.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wordsnchars.ViewModelTextEditor
import com.wordsnchars.text_editor.core.custom_spans.subscriptionFlag
import com.wordsnchars.text_editor.core.custom_spans.superscriptionFlag
import com.wordsnchars.ui_compose.utils.ToolbarButton


@Composable
fun ToolbarHome(viewModelTextEditor: ViewModelTextEditor, directions: TextEditorToolbarDirections) {
    Box {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            val styleApplied = viewModelTextEditor.styled.collectAsState()
            val style = viewModelTextEditor.style.collectAsState()
            ToolbarButton(
                modifier = Modifier.weight(1f),
                enabled = styleApplied,
                onClick = viewModelTextEditor::styledToggle,
                onLongClick = directions::navigateToStyleSelector
            ) {
                Text(
                    text = when (style.value) {
                        NORMAL -> "Style"
                        BOLD -> "Bold"
                        ITALIC -> "Italic"
                        else -> "B&It"
                    },
                    fontSize = 18.sp,
                    fontWeight = when (style.value) {
                        BOLD -> FontWeight.Bold
                        BOLD_ITALIC -> FontWeight.Bold
                        else -> FontWeight.Normal
                    },
                    fontStyle = when (style.value) {
                        ITALIC -> FontStyle.Italic
                        BOLD_ITALIC -> FontStyle.Italic
                        else -> FontStyle.Normal
                    }
                )
            }
            val underlined = viewModelTextEditor.underlined.collectAsState()
            ToolbarButton(
                modifier = Modifier.weight(1f),
                enabled = underlined,
                onClick = viewModelTextEditor::underlinedToggle,
                onLongClick = {}
            ) {
                Text(
                    text = "U",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(300),
                    textDecoration = TextDecoration.Underline
                )
            }
            val highlighted = viewModelTextEditor.highlighted.collectAsState()
            val highlightColor = viewModelTextEditor.highlightColor.collectAsState()
            ToolbarButton(
                modifier = Modifier.weight(1f),
                enabled = highlighted,
                onClick = viewModelTextEditor::highlightToggle,
                onLongClick = directions::navigateToColorSelector
            ) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                        .background(Color.White, RoundedCornerShape(20.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(highlightColor.value), RoundedCornerShape(20.dp))
                    )
                }
            }
            val sized = viewModelTextEditor.sized.collectAsState()
            val size = viewModelTextEditor.fontSizeMultiplier.collectAsState()
            ToolbarButton(
                modifier = Modifier.weight(1f),
                enabled = sized,
                onClick = viewModelTextEditor::sizedToggle,
                onLongClick = directions::navigateToSizeSelector
            ) {
                Text(
                    text = "${size.value}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(300),
                )
            }
            val scripted = viewModelTextEditor.scripted.collectAsState()
            val scription = viewModelTextEditor.scription.collectAsState()
            ToolbarButton(
                modifier = Modifier.weight(1f),
                enabled = scripted,
                onClick = viewModelTextEditor::scriptedToggle,
                onLongClick = directions::navigateToScriptionSelector
            ) {
                Row {
                    Text(
                        text = "S",
                        fontSize = 23.sp,
                        fontWeight = FontWeight(300),
                    )
                    Text(
                        text = "2",
                        fontSize = if (scription.value == 0) 23.sp else 15.sp,
                        fontWeight = FontWeight(300),
                        modifier = Modifier.offset(
                            0.dp,
                            when (scription.value) {
                                subscriptionFlag -> 8.dp
                                superscriptionFlag -> (-8).dp
                                else -> 0.dp
                            }
                        )
                    )
                }
            }
        }
    }
}
