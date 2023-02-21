package com.wordsnchars.text_editor.core

import android.text.Selection
import android.text.SpanWatcher
import android.text.Spannable
import android.widget.EditText

class SelectionWatcher(
    val onCursorChange: (Int) -> Unit,
    val onSelectionChange: (Int, Int) -> Unit,
) : SpanWatcher {
    override fun onSpanAdded(text: Spannable?, what: Any?, start: Int, end: Int) {}
    override fun onSpanRemoved(text: Spannable?, what: Any?, start: Int, end: Int) {}
    override fun onSpanChanged(
        text: Spannable?,
        what: Any,
        ostart: Int,
        oend: Int,
        nstart: Int,
        nend: Int,
    ) {
        if (what::class.java == Selection.SELECTION_END::class.java)
        //detect simple cursor change
            if (text?.getSpanStart(Selection.SELECTION_START) == text?.getSpanStart(Selection.SELECTION_END))
                onCursorChange(nend)
            //detect select
            else
                onSelectionChange(
                    text?.getSpanStart(Selection.SELECTION_START) ?: 0,
                    text?.getSpanStart(Selection.SELECTION_END) ?: 0
                )


    }
}