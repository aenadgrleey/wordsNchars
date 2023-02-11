package com.example.wordsnchars.text_editor

import android.text.Selection
import android.text.SpanWatcher
import android.text.Spannable
import android.widget.EditText

class SelectionWatcher(
    private val editText: EditText,
    val onCursorChange: () -> Unit,
    val onSelectionChange: () -> Unit,
) : SpanWatcher {
    override fun onSpanAdded(text: Spannable?, what: Any?, start: Int, end: Int) {
    }

    override fun onSpanRemoved(text: Spannable?, what: Any?, start: Int, end: Int) {

    }

    override fun onSpanChanged(
        text: Spannable?,
        what: Any,
        ostart: Int,
        oend: Int,
        nstart: Int,
        nend: Int,
    ) {
        if (what::class.java == Selection.SELECTION_END::class.java
            && editText.selectionStart == editText.selectionEnd
        ) onCursorChange()
    }
}