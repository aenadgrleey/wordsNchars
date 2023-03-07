package com.wordsnchars.ui_compose.selectors

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wordsnchars.ui_compose.utils.ToolbarSurface

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SizeSelector(current: State<Float>, sizeSet: (Float) -> Unit, navigateUp: () -> Unit) {
    val items = createItems()
    val state = rememberLazyListState((current.value - 0.5f).div(0.25f).toInt())
    val (height, width) = LocalConfiguration.current.run { screenHeightDp.dp to screenWidthDp.dp }

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        state = state,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
        contentPadding = PaddingValues(
            start = width.div(2).minus(100.dp),
            end = width.div(2).minus(100.dp)
        )
    ) {
        items(items.size) {
            Card(
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    .width(200.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(40.dp)),
                onClick = {
                    sizeSet(items[it])
                    navigateUp()
                }
            ) {
                Text(
                    items[it].toString(),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

fun createItems(): List<Float> {
    var result = mutableListOf<Float>()
    for (i in 0..8) result.add(i * 0.25F + 0.5F)
    return result
}