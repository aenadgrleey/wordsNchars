package com.example.xconspectus.ui.text_editor

import android.text.SpanWatcher
import android.text.Spannable
import android.util.Log

class RemovalWatcher(
    private val previouslySetSpans: MutableMap<Any, MutableSet<Pair<Any, Border>>>,
) : SpanWatcher {
    private val TAG = "RemovalWatcher"

    override fun onSpanAdded(text: Spannable?, what: Any?, start: Int, end: Int) {
    }

    override fun onSpanRemoved(text: Spannable?, what: Any?, start: Int, end: Int) {
        if (what == null) return
        if (what::class.java in previouslySetSpans.keys) {
            previouslySetSpans[what::class.java]!!.add(Pair(what, Border(start, end)))
            Log.v(
                TAG,
                "cached span that system removed for no reason span:$what start:$start end:$end"
            )
        }
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