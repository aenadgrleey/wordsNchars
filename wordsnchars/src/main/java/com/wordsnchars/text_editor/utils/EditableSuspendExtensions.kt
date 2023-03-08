package com.wordsnchars.text_editor.utils

import android.text.Editable
import android.text.Spannable
import android.util.Log
import kotlin.math.max
import kotlin.math.min


/**
 *find out if span is being inserted into another one of same type and create a gap in given borders
 */
fun Editable.createGap(spanType: Any, border: Border) {
    if (border.hasZeroLength()) return
    this.getSpans(border.start, border.end, spanType as Class<*>).forEach { span ->
        val spanBorder = this.getBorder(span)
        if (border isOverlappedBy spanBorder) {
            Log.v("CreateGap", "clearing border $spanBorder")
            val afterSpanBorder = Border(border.end, max(spanBorder.end, border.end))
            println("$afterSpanBorder")
            this.setSpan(span.copySpan(), afterSpanBorder)

            val beforeSpanBorder = Border(min(spanBorder.start, border.start), border.start)
            when (this.setSpan(span, beforeSpanBorder)) {
                false -> this.removeSpan(span)
                true -> {
                    println("not today")
                }

            }
        }
    }
}

//check if text holds similar span in the given borders
fun Editable.hasSimilar(
    spanToCheck: Any,
    border: Border,
): Boolean {
    //get all similar spans on the string being edited
    this.getSpans(border.start, border.end, spanToCheck::class.java).forEach {
        if (it.hasSameAttributes(spanToCheck)
            && border inside this.getBorder(it)
        ) return true
    }
    return false

}

fun Editable.setSpan(span: Any, border: Border): Boolean {
    val convenientBorder =
        Border(max(min(border.start, length), 0), minOf(border.end, length))
    //check if there's mess with border ends
    if (convenientBorder.hasZeroLength()
    ) return false
    setSpan(
        span,
        convenientBorder.start, convenientBorder.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return true
}
