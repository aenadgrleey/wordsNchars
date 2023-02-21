package com.wordsnchars

import android.text.InputType
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.viewModelScope
import com.wordsnchars.text_editor.core.RemovalWatcher
import com.wordsnchars.text_editor.core.SelectionWatcher
import com.wordsnchars.text_editor.core.TextWatcherTextEditor
import com.wordsnchars.text_editor.utils.Border
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


val supportedSpans =
    listOf(
        BackgroundColorSpan::class.java,
        StyleSpan::class.java,
        UnderlineSpan::class.java,
        RelativeSizeSpan::class.java
    )

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
            textWatcher.currentSpansStarts[BackgroundColorSpan::class.java] = editText.selectionStart
            textWatcher.insertLength[BackgroundColorSpan::class.java] =
                editText.run { selectionEnd - selectionStart }
        }.launchIn(viewModel.viewModelScope)

        viewModel.style.onEach {
            textWatcher.currentSpansStarts[StyleSpan::class.java] = editText.selectionStart
            textWatcher.insertLength[StyleSpan::class.java] =
                editText.run { selectionEnd - selectionStart }
        }.launchIn(viewModel.viewModelScope)

        viewModel.fontSizeMultiplier.onEach {
            textWatcher.currentSpansStarts[RelativeSizeSpan::class.java] = editText.selectionStart
            textWatcher.insertLength[RelativeSizeSpan::class.java] =
                editText.run { selectionEnd - selectionStart }

        }.launchIn(viewModel.viewModelScope)

        viewModel.underlined.onEach {
            textWatcher.currentSpansStarts[UnderlineSpan::class.java] = editText.selectionStart
            textWatcher.insertLength[UnderlineSpan::class.java] =
                editText.run { selectionEnd - selectionStart }
        }.launchIn(viewModel.viewModelScope)

    }

    private fun onCursorChange(cursorPosition: Int): Unit {
        textWatcher.cursorPosition = cursorPosition
        Log.v(TAG, "changed cursor pos to $cursorPosition")

        //detect change of cursor position cased not by insert and reset start point and length of spans
        if (!textWatcher.triggered) {
            textWatcher.start = cursorPosition
            textWatcher.currentSpansStarts.keys.forEach {
                textWatcher.currentSpansStarts[it] = textWatcher.cursorPosition
            }
            textWatcher.insertLength.keys.forEach { textWatcher.insertLength[it] = 0 }
        }
        textWatcher.triggered = false

    }

    private fun onSpanDelete(span: Any?, border: Border): Unit {
        if (span == null) return
        if (span::class.java in supportedSpans) {
            viewModel.previouslySetSpans[span::class.java]!!.add(Pair(span, border))
            Log.v(TAG, "detected delete $span $border")
        }
    }

    private fun onSelect(selectionStart: Int, selectionEnd: Int): Unit {
//        supportedSpans.forEach {
//            textWatcher.currentSpansStarts[it] = selectionStart
//            textWatcher.insertLength[it] = selectionEnd - selectionStart
//        }
    }
}
