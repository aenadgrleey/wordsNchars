package com.wordsnchars.ui_compose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ToolbarSurface(content: @Composable () -> Unit) {
    Surface(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .height(75.dp)
            .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
            .padding(top = 4.dp),
        shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp),
        elevation = 4.dp
    ) {
        content()
    }
}