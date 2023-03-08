package com.wordsnchars.text_editor.core

import android.text.SpanWatcher
import android.text.Spannable
import android.text.style.UnderlineSpan

class UnderlineRestrictor : SpanWatcher {
    override fun onSpanAdded(text: Spannable, what: Any, start: Int, end: Int) {
        if (what::class.java == UnderlineSpan::class.java)
            text.removeSpan(what)
    }

    override fun onSpanRemoved(text: Spannable?, what: Any?, start: Int, end: Int) {}

    override fun onSpanChanged(
        text: Spannable,
        what: Any,
        ostart: Int,
        oend: Int,
        nstart: Int,
        nend: Int
    ) {
        if (what::class.java == UnderlineSpan::class.java)
            text.removeSpan(what)
    }
}