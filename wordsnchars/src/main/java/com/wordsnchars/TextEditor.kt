package com.wordsnchars

import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.text.style.StyleSpan
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

        //detect change of modifier and reset start point and length of spans

        viewModel.highlightColor.onEach {
            viewModel.currentSpansStarts[BackgroundColorSpan::class.java] = editText.selectionStart
            textWatcher.insertLength[BackgroundColorSpan::class.java] = 0
        }.launchIn(viewModel.viewModelScope)

        viewModel.style.onEach {
            viewModel.currentSpansStarts[StyleSpan::class.java] = editText.selectionStart
            textWatcher.insertLength[StyleSpan::class.java] = 0
        }.launchIn(viewModel.viewModelScope)

    }

    private fun onCursorChange(cursorPosition: Int): Unit {
        viewModel.cursorPosition = editText.selectionEnd

        //detect change of cursor position cased not by insert and reset start point and length of spans
        if (!textWatcher.triggered) {
            viewModel.currentSpansStarts.run {
                this.keys.forEach {
                    this[it] = editText.selectionStart
                }
            }
            textWatcher.insertLength.keys.forEach {
                textWatcher.insertLength[it] = 0
            }
        }
        textWatcher.triggered = false

    }

    private fun onSelect(): Unit {
        TODO()
    }

    private fun onSpanDelete(span: Any?, border: Border): Unit {
        if (span == null) return
        if (span::class.java in viewModel.previouslySetSpans.keys) {
            viewModel.previouslySetSpans[span::class.java]!!.add(Pair(span, border))
        }
    }
}