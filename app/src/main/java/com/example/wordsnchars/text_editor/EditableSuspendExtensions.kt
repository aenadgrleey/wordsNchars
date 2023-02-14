package com.example.wordsnchars.text_editor

import android.text.Editable
import android.text.Spannable
import android.text.style.BackgroundColorSpan


//find out if span is being inserted into another one of same type and create a gap in given borders
fun Editable.createGap(spanType: Any, border: Border) {
    if (border.hasImproperLength()) return
    this.getSpans(border.start, border.end, spanType as Class<*>).forEach { span ->
        val spanStart = this.getSpanStart(span)
        val spanEnd = this.getSpanEnd(span)
        val spanBorder = Border(spanStart, spanEnd)
        //if span is
        if (border isOverlayedBy spanBorder) {
            val beforeSpanBorder = Border(spanStart, border.start)
            val afterSpanBorder = Border(border.end, spanEnd)
            this.setSpan(span, afterSpanBorder)
            this.setSpan(span.copySpan(), beforeSpanBorder)
        }

    }
}

//check if text holds similar span in the given borders
fun Editable.hasSimilar(
    spanToCheck: Any,
    border: Border,
): Boolean {
    //get all similar spans on the string being edited
    val spans = this.getSpans(border.start, border.end, spanToCheck::class.java)
    spans.forEach {
        if (it?.hasSameAttributes(spanToCheck) == true
            && this.getSpanEnd(it) >= border.end
            && this.getSpanStart(it) <= border.start
        ) return true
    }
    return false

}

fun Editable.setSpan(span: Any, border: Border): Boolean {
    //check if there's mess with border ends
    if (this.hasSimilar(span, border)
        || border.hasImproperLength()
    ) return false
    println("setting span: $span ${(span as BackgroundColorSpan).backgroundColor}, $border")
    setSpan(
        span, minOf(border.start, length), minOf(border.end, length), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return true
}
