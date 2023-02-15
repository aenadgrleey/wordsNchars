package com.wordsnchars.text_editor.core

import android.text.Selection
import android.text.SpanWatcher
import android.text.Spannable
import android.widget.EditText

class SelectionWatcher(
    private val editText: EditText,
    val onCursorChange: (Int) -> Unit,
    val onSelectionChange: () -> Unit,
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
            if (text?.getSpanStart(Selection.SELECTION_START) == text?.getSpanStart(Selection.SELECTION_END))
                onCursorChange(nstart)
            else TODO()
    }
}