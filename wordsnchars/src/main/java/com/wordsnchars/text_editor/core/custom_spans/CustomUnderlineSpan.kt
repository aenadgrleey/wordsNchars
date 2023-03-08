package com.wordsnchars.text_editor.core.custom_spans

import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance


internal class CustomUnderlineSpan(val underlined: Boolean) : CharacterStyle(),
    UpdateAppearance {
    override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = underlined
    }
}