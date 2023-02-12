package com.example.wordsnchars.text_editor

import android.text.SpanWatcher
import android.text.Spannable
import android.util.Log

class RemovalWatcher(
    private val onDelete: (Any?, Border) -> Unit
) : SpanWatcher {
    private val TAG = "RemovalWatcher"

    override fun onSpanAdded(text: Spannable?, what: Any?, start: Int, end: Int) {
    }

    override fun onSpanRemoved(text: Spannable?, what: Any?, start: Int, end: Int) {
        onDelete(what, Border(start, end))
        Log.v(
            TAG,
            "span that system removed for no reason span:$what start:$start end:$end"
        )
    }

    override fun onSpanChanged(
        text: Spannable?,
        what: Any?,
        ostart: Int,
        oend: Int,
        nstart: Int,
        nend: Int,
    ) {
    }
}