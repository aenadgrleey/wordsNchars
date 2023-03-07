package com.wordsnchars.ui_compose.selectors

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wordsnchars.text_editor.core.custom_spans.subscriptionFlag
import com.wordsnchars.text_editor.core.custom_spans.superscriptionFlag
import com.wordsnchars.ui_compose.utils.ToolbarButton

@Composable
fun ScriptionSelector(scriptionSet: (Int) -> Unit, navigateUp: () -> Unit) {
    Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
        //normal
        ToolbarButton(
            onClick = {
                scriptionSet(subscriptionFlag)
                navigateUp()
            },
            onLongClick = {
                scriptionSet(subscriptionFlag)
                navigateUp()
            },
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            enabled = remember { mutableStateOf(false) }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Subscription: ",
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
                Text(
                    text = "S",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(300),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = "2",
                    fontSize = 15.sp,
                    fontWeight = FontWeight(300),
                    modifier = Modifier.offset(0.dp, 10.dp)
                )
            }
        }
        ToolbarButton(
            onClick = {
                scriptionSet(superscriptionFlag)
                navigateUp()
            },
            onLongClick = {
                scriptionSet(superscriptionFlag)
                navigateUp()
            },
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            enabled = remember { mutableStateOf(false) }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Superscription: ")
                Text(
                    text = "S",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(300),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = "2",
                    fontSize = 15.sp,
                    fontWeight = FontWeight(300),
                    modifier = Modifier.run { offset(0.dp, (-10).dp) }
                )
            }
        }
    }
}