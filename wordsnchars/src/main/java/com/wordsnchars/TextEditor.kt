package com.wordsnchars

import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.viewModelScope
import com.wordsnchars.text_editor.core.RemovalWatcher
import com.wordsnchars.text_editor.core.SelectionWatcher
import com.wordsnchars.text_editor.core.TextWatcherTextEditor
import com.wordsnchars.text_editor.utils.Border
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TextEditor(
    private val viewModel: ViewModelTextEditor,
    private val editText: EditText,
) {
    private val textWatcher = TextWatcherTextEditor(viewModel)

    private val TAG = "TextEditor"

    init {
        editText.addTextChangedListener(textWatcher)

        //mainly done with inserting
        editText.text.setSpan(
            SelectionWatcher(
                editText,
                this@TextEditor::onCursorChange,
                this@TextEditor::onSelect
            ),
            0,
            0,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        editText.text.setSpan(
            RemovalWatcher(this@TextEditor::onSpanDelete),
            0, 0, Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        viewModel.highlightColor.onEach {
            Log.v(TAG, "changing insertion point to ${editText.selectionStart}")
            viewModel.currentSpansStarts[BackgroundColorSpan::class.java] = editText.selectionStart
            textWatcher.insertLength = 0
        }.launchIn(viewModel.viewModelScope)
    }

    private fun onCursorChange(): Unit {
        val delta = editText.selectionStart - viewModel.cursorPosition
        viewModel.cursorPosition = editText.selectionStart
        Log.v(TAG, "cursor change ${editText.selectionStart}")
        //detect radical change
        if (!textWatcher.triggered) {
            Log.v(
                TAG,
                "detected radical change in cursor position ${viewModel.cursorPosition} $delta ${textWatcher.delta}"
            )
            viewModel.currentSpansStarts.run {
                this.keys.forEach {
                    this[it] = editText.selectionStart
                }
            }
            textWatcher.insertLength = 0
        }
        textWatcher.triggered = false

    }

    private fun onSelect(): Unit {
        Log.v(
            this::class.java.toString(),
            "select ${editText.selectionStart} ${editText.selectionEnd}"
        )
    }

    private fun onSpanDelete(span: Any?, border: Border): Unit {
        if (span == null) return
        if (span::class.java in viewModel.previouslySetSpans.keys) {
            viewModel.previouslySetSpans[span::class.java]!!.add(Pair(span, border))
        }
    }
}