package com.wordsnchars.ui_compose.selectors

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wordsnchars.text_editor.core.custom_spans.subscriptionFlag
import com.wordsnchars.text_editor.core.custom_spans.superscriptionFlag

@Composable
fun ScriptionSelector(scriptionSet: (Int) -> Unit, navigateUp: () -> Unit) {
    Surface(
        Modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
            //normal
            ElevatedButton(
                shape = CircleShape,
                onClick = {
                    scriptionSet(subscriptionFlag)
                    navigateUp()
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(4.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp,
                    disabledElevation = 0.dp
                )
            ) {
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
            ElevatedButton(
                shape = CircleShape,
                onClick = {
                    scriptionSet(superscriptionFlag)
                    navigateUp()
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(4.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp,
                    disabledElevation = 0.dp
                )
            ) {
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