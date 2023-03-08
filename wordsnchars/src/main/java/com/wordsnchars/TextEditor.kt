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
import com.wordsnchars.text_editor.core.UnderlineRestrictor
import com.wordsnchars.text_editor.core.custom_spans.CustomUnderlineSpan
import com.wordsnchars.text_editor.core.custom_spans.ScriptionSpan
import com.wordsnchars.text_editor.utils.Border
import com.wordsnchars.text_editor.utils.setSpan
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


val supportedSpans =
    listOf(
        BackgroundColorSpan::class.java,
        StyleSpan::class.java,
        CustomUnderlineSpan::class.java,
        ScriptionSpan::class.java,
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
            0, 0, Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        editText.text.setSpan(
            RemovalWatcher(this@TextEditor::onSpanDelete),
            0, 0, Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        editText.text.setSpan(UnderlineRestrictor(),
            0, 0, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        //detect change of modifier and reset start point and length of spans
        viewModel.run {
            highlightColor.onEach {
                val span = BackgroundColorSpan(it)
                onValueUpdate(span)
            }.launchIn(viewModelScope)

            highlighted.onEach {
                val span = BackgroundColorSpan(highlightColor.value)
                onValueUpdate(span)
            }.launchIn(viewModelScope)

            style.onEach {
                val span = StyleSpan(it)
                onValueUpdate(span)
            }.launchIn(viewModelScope)

            styled.onEach {
                val span = StyleSpan(style.value)
                onValueUpdate(span)
            }.launchIn(viewModelScope)

            fontSizeMultiplier.onEach {
                val span = RelativeSizeSpan(it)
                onValueUpdate(span)
            }.launchIn(viewModelScope)

            sized.onEach {
                val span = RelativeSizeSpan(fontSizeMultiplier.value)
                onValueUpdate(span)
            }.launchIn(viewModelScope)

            underlined.onEach {
                val span = CustomUnderlineSpan(it)
                onValueUpdate(span)
            }.launchIn(viewModelScope)

            scription.onEach {
                val span = ScriptionSpan(it)
                onValueUpdate(span)
            }.launchIn(viewModelScope)

            scripted.onEach {
                val span = ScriptionSpan(scription.value)
                onValueUpdate(span)
            }.launchIn(viewModelScope)

        }
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

    private fun onValueUpdate(span: Any) {
        textWatcher.currentSpansStarts[span::class.java] =
            editText.selectionStart
        textWatcher.insertLength[span::class.java] =
            editText.run { selectionEnd - selectionStart }
        if (textWatcher.insertLength[span::class.java] != 0) {
            //should test if this line doesn't cause any problems, because seems very scary
            textWatcher.start = 0
            textWatcher.handleModifier(editText.text, span)
        }
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
