package com.wordsnchars.text_editor.utils

import android.text.Editable
import android.text.style.BackgroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan


/**
 *returns a copy of a span
 */
fun Any.copySpan(): Any {
    if (this::class.java == BackgroundColorSpan::class.java) {
        val color = (this as BackgroundColorSpan).backgroundColor
        return BackgroundColorSpan(color)
    }
    if (this::class.java == StyleSpan::class.java) {
        val style = (this as StyleSpan).style
        return StyleSpan(style)
    }

    if (this::class.java == RelativeSizeSpan::class.java) {
        val multiplier = (this as RelativeSizeSpan).sizeChange
        return RelativeSizeSpan(multiplier)
    }

    if (this::class.java == UnderlineSpan::class.java) return UnderlineSpan()

    throw Exception("Tried to copy unsupported span ${this}")
}

/**
 *compares and returns true if span has same attributes as the one being compared with
 */
fun Any.hasSameAttributes(spanToCompare: Any): Boolean {
    if (this::class.java == BackgroundColorSpan::class.java
        && spanToCompare::class.java == BackgroundColorSpan::class.java
    ) return (this as BackgroundColorSpan).backgroundColor == (spanToCompare as BackgroundColorSpan).backgroundColor
    if (this::class.java == StyleSpan::class.java
        && spanToCompare::class.java == StyleSpan::class.java
    ) return (this as StyleSpan).style == (spanToCompare as StyleSpan).style
    if (this::class.java == RelativeSizeSpan::class.java
        && spanToCompare::class.java == RelativeSizeSpan::class.java
    ) return (this as RelativeSizeSpan).sizeChange == (spanToCompare as RelativeSizeSpan).sizeChange
    if (this::class.java == UnderlineSpan::class.java
        && spanToCompare::class.java == UnderlineSpan::class.java
    ) return true
    throw Exception("Tried to compare unsupported spans $this $spanToCompare")
}

/**
 * returns a Border object with borders of span
 */
fun Editable.getBorder(span: Any): Border {
    //may initialize with border(-1, -1)
    return Border(this.getSpanStart(span), this.getSpanEnd(span))
}
