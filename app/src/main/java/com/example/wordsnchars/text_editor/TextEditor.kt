package com.example.xconspectus.ui.text_editor

import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.FieldPosition
import kotlin.math.absoluteValue

class TextEditor(private val viewModel: ViewModelTextEditor, private val editText: EditText) {
    private val TAG = "TextEditor"

    private val textWatcher = TextWatcherTextEditor(viewModel)

    init {
        editText.addTextChangedListener(textWatcher)

        //not implemented yet
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
            RemovalWatcher(viewModel.previouslySetSpans),
            0, 0, Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        viewModel.highlightColor.onEach {
            Log.v(TAG, "changing insertion point to ${editText.selectionStart}")
            viewModel.currentSpansStarts[BackgroundColorSpan::class.java] = editText.selectionStart
            textWatcher.insertLength = 1
        }.launchIn(viewModel.viewModelScope)
    }

    private fun onCursorChange(): Unit {
        val delta = editText.selectionStart - viewModel.cursorPosition
        viewModel.cursorPosition = editText.selectionStart
        Log.v(this::class.java.toString(), "cursor change ${editText.selectionStart}")
        //detect radical change
        if (delta != textWatcher.delta) {
            Log.v(
                this::class.java.toString(),
                "detected radical change in cursor position ${viewModel.cursorPosition} $delta ${textWatcher.delta}"
            )
            viewModel.currentSpansStarts.run {
                this.keys.forEach {
                    this[it] = editText.selectionStart
                }
            }
            textWatcher.insertLength = 0
        }
    }

    private fun onSelect(): Unit {
        Log.v(
            this::class.java.toString(),
            "segesg ${editText.selectionStart} ${editText.selectionEnd}"
        )
    }
}