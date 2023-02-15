package com.wordsnchars.text_editor

import android.text.Editable
import android.text.style.BackgroundColorSpan
import com.wordsnchars.text_editor.utils.Border

//returns a copy of a span
fun Any.copySpan(): Any {
    if (this::class.java == BackgroundColorSpan::class.java) {
        val color = (this as BackgroundColorSpan).backgroundColor
        return BackgroundColorSpan(color)
    }
    throw Exception("Tried to copy unsupported span ${this}")
}

//compares and returns true if span has same attributes as the one being compared with
fun Any.hasSameAttributes(spanToCompare: Any): Boolean {
    if (this::class.java == BackgroundColorSpan::class.java
        && spanToCompare::class.java == BackgroundColorSpan::class.java
    ) return (this as BackgroundColorSpan).backgroundColor == (spanToCompare as BackgroundColorSpan).backgroundColor

    throw Exception("Tried to compare unsupported spans $this $spanToCompare")
}

fun Editable.getBorder(span: Any): Border {
    return Border(this.getSpanStart(span), this.getSpanEnd(span))
}