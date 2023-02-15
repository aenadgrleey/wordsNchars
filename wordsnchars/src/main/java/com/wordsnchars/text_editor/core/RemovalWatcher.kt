package com.wordsnchars.text_editor.core

import android.text.SpanWatcher
import android.text.Spannable
import com.wordsnchars.text_editor.utils.Border

class RemovalWatcher(
    private val onDelete: (Any?, Border) -> Unit
) : SpanWatcher {

    private val TAG = "RemovalWatcher"

    override fun onSpanRemoved(text: Spannable?, what: Any?, start: Int, end: Int) {
        onDelete(what, Border(start, end))
    }

    override fun onSpanAdded(text: Spannable?, what: Any?, start: Int, end: Int) {}
    override fun onSpanChanged(text: Spannable?, what: Any?, ostart: Int, oend: Int, nstart: Int, nend: Int) {}
}