package com.wordsnchars.text_editor.core.custom_spans

import android.os.Build
import android.os.Parcel
import android.text.ParcelableSpan
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.CharacterStyle
import android.text.style.UnderlineSpan
import android.text.style.UpdateAppearance
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp


internal class CustomUnderlineSpan(val underlined: Boolean) : CharacterStyle(),
    UpdateAppearance {
    override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = underlined
    }
}