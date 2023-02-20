package com.wordsnchars.text_editor.utils

import android.text.Editable
import android.text.Spannable
import kotlin.math.max
import kotlin.math.min
import kotlin.text.min as mi


/**
 *find out if span is being inserted into another one of same type and create a gap in given borders
 */
fun Editable.createGap(spanType: Any, border: Border) {
    if (border.hasZeroLength()) return
    this.getSpans(border.start, border.end, spanType as Class<*>).forEach { span ->
        val spanBorder = this.getBorder(span)
        if (border isOverlappedBy spanBorder) {
            try {
                val afterSpanBorder = Border(border.end, spanBorder.end)
                this.setSpan(
                    span.copySpan(),
                    afterSpanBorder
                )

            } catch (e: ImproperBordersException){}
            try {
                val beforeSpanBorder = Border(spanBorder.start, border.start)
                if (!this.setSpan(
                        span,
                        beforeSpanBorder
                    )
                ) this.removeSpan(span)
            } catch (e: ImproperBordersException){}
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
        val anotherBorder = Border(this.getSpanStart(it), this.getSpanEnd(it))
        if (it?.hasSameAttributes(spanToCheck) == true
            && border inside anotherBorder
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
